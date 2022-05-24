package com.google.developers.lettervault.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Model class holds information about the letter and defines table for the Room
 * database with auto generate primary key.
 */
@Entity(tableName = "letter.db")
data class Letter(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "letter_id" )
    val id: Long = 0,
    @ColumnInfo(name = "letter_subject" )
    val subject: String,
    @ColumnInfo(name = "letter_content" )
    val content: String,
    @ColumnInfo(name = "letter_created" )
    val created: Long,
    @ColumnInfo(name = "letter_expires" )
    val expires: Long,
    @ColumnInfo(name = "letter_opened" )
    val opened: Long = 0
)
