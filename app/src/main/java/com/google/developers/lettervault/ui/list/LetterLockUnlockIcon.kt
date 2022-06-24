package com.google.developers.lettervault.ui.list

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.google.developers.lettervault.R

class LetterLockUnlockIcon:
    AppCompatImageView{
    private var mState = 0
   constructor (context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
       context,
       attrs,
       defStyleAttr )
    constructor(context: Context, attrs: AttributeSet?) : super(context,attrs)
    constructor(context: Context) : super(context)

    var state : Int
    get() = mState
    set(state) {
        when (state) {
        LOCK -> setImageResource(R.drawable.ic_lock)
            UNLOCK -> setImageResource(R.drawable.ic_lock_open)
        }
    }
    companion object {
        const val LOCK = 0
        const val UNLOCK = 1

    }
}