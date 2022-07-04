package me.dio.soccernews.data.remote

import android.telecom.Call
import me.dio.soccernews.domain.News
import retrofit2.Call
import retrofit2.http.GET

interface SoccerNewsApi {
    @get:GET("news.json")
    val news: Call<List<News?>?>?
}