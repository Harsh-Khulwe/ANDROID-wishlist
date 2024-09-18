package com.example.wishlistapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "WishTable")
data class Wish(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ColumnInfo("wish-name")
    val wish: String = "",
    @ColumnInfo("wish-desc")
    val wishDescription: String = ""
)

object DummyWish {
    var wishList: List<Wish> = listOf(
        Wish(
            wish = "Meet Emma Watson",
            wishDescription = "Going to England and fixing an appointment with Emma to meet her personally.."
        ),
        Wish(
            wish = "Become an Android Developer",
            wishDescription = "Working hard on the topics and then will make some good real life projects to showcase them"
        ),
        Wish(
            wish = "Be healthy",
            wishDescription = "Exercise on a daily basis to keep good and healthy body"
        )
    )
}
