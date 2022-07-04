package me.dio.soccernews.data

import androidx.room.Room
import me.dio.soccernews.App
import me.dio.soccernews.data.local.SoccerNewsDb
import me.dio.soccernews.data.remote.SoccerNewsApi
import retrofit2.converter.gson.GsonConverterFactory

class SoccerNewsRepository private constructor() {
    //endregion
    //region Atributos: encapsulam o acesso a nossa API (Retrofit) e banco de dados local (Room).
    private val remoteApi: SoccerNewsApi
    private val localDb: SoccerNewsDb
    fun getRemoteApi(): SoccerNewsApi {
        return remoteApi
    }

    fun getLocalDb(): SoccerNewsDb {
        return localDb
    }

    private object LazyHolder {
        val instance = SoccerNewsRepository()
            get() = LazyHolder.field
    } //endregion

    companion object {
        //region Constantes
        private const val REMOTE_API_URL = "https://digitalinnovationone.github.io/soccer-news-api/"
        private const val LOCAL_DB_NAME = "soccer-news"
    }

    //endregion
    //region Singleton: garante uma instância única dos atributos relacionados ao Retrofit e Room.
    init {
        remoteApi = Builder()
            .baseUrl(REMOTE_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SoccerNewsApi::class.java)
        localDb = Room.databaseBuilder(
            App.getInstance(),
            SoccerNewsDb::class.java, LOCAL_DB_NAME
        ).build()
    }
}