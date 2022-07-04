package me.dio.soccernews.ui.favorites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import me.dio.soccernews.databinding.FragmentFavoritesBinding
import me.dio.soccernews.domain.News
import me.dio.soccernews.ui.adapters.NewsAdapter

class FavoritesFragment : Fragment() {
    private var binding: FragmentFavoritesBinding? = null
    private var favoritesViewModel: FavoritesViewModel? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        favoritesViewModel = ViewModelProvider(this).get(FavoritesViewModel::class.java)
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        loadFavoriteNews()
        return binding!!.root
    }

    private fun loadFavoriteNews() {
        favoritesViewModel!!.loadFavoriteNews().observe(
            viewLifecycleOwner
        ) { localNews: List<News?>? ->
            binding.rvNews.setLayoutManager(LinearLayoutManager(context))
            binding.rvNews.setAdapter(
                NewsAdapter(
                    localNews
                ) { updatedNews: News? ->
                    favoritesViewModel!!.saveNews(updatedNews)
                    loadFavoriteNews()
                }
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}