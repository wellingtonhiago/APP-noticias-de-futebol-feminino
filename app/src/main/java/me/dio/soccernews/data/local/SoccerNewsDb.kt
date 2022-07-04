package me.dio.soccernews.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import me.dio.soccernews.domain.News

@Database(entities = [News::class], version = 1)
abstract class SoccerNewsDb : RoomDatabase() {
    abstract fun newsDao(): NewsDao?
}