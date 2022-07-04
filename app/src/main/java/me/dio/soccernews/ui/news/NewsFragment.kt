package me.dio.soccernews.ui.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import me.dio.soccernews.R
import me.dio.soccernews.databinding.FragmentNewsBinding
import me.dio.soccernews.ui.adapters.NewsAdapter

class NewsFragment : Fragment() {
    private var binding: FragmentNewsBinding? = null
    private var newsViewModel: NewsViewModel? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        newsViewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        binding = FragmentNewsBinding.inflate(inflater, container, false)
        val root: View = binding!!.root
        binding.rvNews.setLayoutManager(LinearLayoutManager(context))
        observeNews()
        observeStates()
        binding.srlNews.setOnRefreshListener(newsViewModel::findNews)
        return root
    }

    private fun observeNews() {
        newsViewModel.getNews().observe(viewLifecycleOwner) { news ->
            binding.rvNews.setAdapter(
                NewsAdapter(news, newsViewModel::saveNews)
            )
        }
    }

    private fun observeStates() {
        newsViewModel.getState().observe(viewLifecycleOwner) { state ->
            when (state) {
                DOING -> binding.srlNews.setRefreshing(true)
                DONE -> binding.srlNews.setRefreshing(false)
                ERROR -> {
                    binding.srlNews.setRefreshing(false)
                    Snackbar.make(binding.srlNews, R.string.error_network, Snackbar.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}