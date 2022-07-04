package me.dio.soccernews.ui.news

import android.os.AsyncTask
import android.telecom.Call
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.dio.soccernews.data.SoccerNewsRepository
import me.dio.soccernews.domain.News
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel : ViewModel() {
    enum class State {
        DOING, DONE, ERROR
    }

    private val news = MutableLiveData<List<News>>()
    private val state = MutableLiveData<State>()
    fun findNews() {
        state.setValue(State.DOING)
        SoccerNewsRepository.getInstance().getRemoteApi().getNews()
            .enqueue(object : Callback<List<News?>?>() {
                fun onResponse(call: Call<List<News?>?>, response: Response<List<News?>?>) {
                    if (response.isSuccessful()) {
                        news.setValue(response.body())
                        state.setValue(State.DONE)
                    } else {
                        state.setValue(State.ERROR)
                    }
                }

                fun onFailure(call: Call<List<News?>?>, error: Throwable) {
                    //FIXME Tirar o printStackTrace quando formos para produção!
                    error.printStackTrace()
                    state.setValue(State.ERROR)
                }
            })
    }

    fun saveNews(news: News?) {
        AsyncTask.execute {
            SoccerNewsRepository.getInstance().getLocalDb().newsDao().save(news)
        }
    }

    fun getNews(): LiveData<List<News>> {
        return news
    }

    fun getState(): LiveData<State> {
        return state
    }

    init {
        findNews()
    }
}