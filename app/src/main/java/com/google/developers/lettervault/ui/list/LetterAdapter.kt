package com.google.developers.lettervault.ui.list

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.developers.lettervault.R
import com.google.developers.lettervault.data.Letter
import com.google.developers.lettervault.ui.detail.LetterDetailActivity
import com.google.developers.lettervault.util.LETTER_ID
import kotlinx.android.synthetic.main.letter_list_item.*

/**
 * Implementation of an Paging adapter that shows list of Letters.
 */
class LetterAdapter(
    private val clickListener: (Letter) -> Unit
) : PagedListAdapter<Letter, LetterViewHolder>(DIFF_CALLBACK) {

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Letter>() {
            override fun areItemsTheSame(oldItem: Letter, newItem: Letter): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Letter, newItem: Letter): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LetterViewHolder {
        return LetterViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.letter_list_item,parent, false))
    }

    override fun onBindViewHolder(holder: LetterViewHolder, position: Int) {
        val letter = getItem(position) as Letter
        holder.bindData(letter){
            val detailIntent = Intent(holder.itemView.context,LetterDetailActivity::class.java)
            detailIntent.putExtra(LETTER_ID, letter.id)
            holder.itemView.context.startActivity(detailIntent)
clickListener(it)
        }


    }




}
