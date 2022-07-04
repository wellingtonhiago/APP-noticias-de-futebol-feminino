package me.dio.soccernews.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class News {
    @PrimaryKey
    var id = 0
    var title: String? = null
    var description: String? = null
    var image: String? = null
    var link: String? = null
    var favorite = false
}