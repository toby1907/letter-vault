package com.google.developers.lettervault.ui.list

import android.view.View
import android.widget.TextView
import androidx.annotation.VisibleForTesting
import androidx.recyclerview.widget.RecyclerView
import com.google.developers.lettervault.R
import com.google.developers.lettervault.data.Letter
import com.google.developers.lettervault.ui.list.LetterLockUnlockIcon.Companion.LOCK
import com.google.developers.lettervault.ui.list.LetterLockUnlockIcon.Companion.UNLOCK
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * View holds a letter for RecyclerView.
 */
class LetterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private lateinit var letter: Letter
    private val context = itemView.context
    private val simpleDate = SimpleDateFormat("MMM d Y, h:mm a", Locale.getDefault())
val letterTitle: TextView = itemView.findViewById(R.id.letter_title)
    val letterStatus: TextView = itemView.findViewById(R.id.open_status)
    val icon : LetterLockUnlockIcon = itemView.findViewById(R.id.letter_icon)

    fun bindData(letter: Letter, clickListener: (Letter) -> Unit) {
        this.letter = letter
        letterTitle.text = letter.subject
        itemView.setOnClickListener { clickListener(letter) }

        if (letter.expires < System.currentTimeMillis() && letter.opened != 0L) {
            val opened =
                context.getString(R.string.title_opened, simpleDate.format(letter.opened))
            letterStatus.text = opened
            icon.state = UNLOCK
        } else {
            if (letter.expires < System.currentTimeMillis()) {
                val ready = context.getString(R.string.letter_ready)
                letterStatus.text = ready
                icon.state = LOCK

            } else {
                val opening = context.getString(R.string.letter_opening, simpleDate.format(letter.expires))
            letterStatus.text = opening
                icon.state = LOCK
            }
        }
    }

    /**
     * This method is used during automated tests.
     *
     * DON'T REMOVE THIS METHOD
     */
    @VisibleForTesting
    fun getLetter(): Letter = letter
}
