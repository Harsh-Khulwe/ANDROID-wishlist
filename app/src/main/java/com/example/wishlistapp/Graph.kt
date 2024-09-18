package com.example.wishlistapp

import android.content.Context
import androidx.room.Room
import com.example.wishlistapp.data.WishDatabase
import com.example.wishlistapp.data.WishRepository

object Graph {
    private lateinit var database: WishDatabase

    val wishRepository by lazy {
        WishRepository(database.wishDao())
    }

    fun databaseInstance(context: Context) {
        database = Room.databaseBuilder(
            context,
            WishDatabase::class.java,
            "WishDB"
        ).build()
    }
}