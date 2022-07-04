package me.dio.soccernews.ui.favorites

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import me.dio.soccernews.data.SoccerNewsRepository
import me.dio.soccernews.domain.News

class FavoritesViewModel : ViewModel() {
    fun loadFavoriteNews(): LiveData<List<News>> {
        return SoccerNewsRepository.getInstance().getLocalDb().newsDao().loadFavoriteNews()
    }

    fun saveNews(news: News?) {
        AsyncTask.execute {
            SoccerNewsRepository.getInstance().getLocalDb().newsDao().save(news)
        }
    }
}