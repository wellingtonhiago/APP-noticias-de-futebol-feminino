package me.dio.soccernews.ui.adapters

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import me.dio.soccernews.R
import me.dio.soccernews.databinding.NewsItemBinding
import me.dio.soccernews.domain.News

class NewsAdapter(news: List<News>, favoriteListener: FavoriteListener) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder?>() {
    private val news: List<News>
    private val favoriteListener: FavoriteListener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.getContext())
        val binding = NewsItemBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val context: Context = holder.itemView.getContext()
        val news: News = news[position]
        holder.binding.tvTitle.setText(news.title)
        holder.binding.tvDescription.setText(news.description)
        Picasso.get().load(news.image).fit().into(holder.binding.ivThumbnail)
        // Implementação da funcionalidade de "Abrir Link":
        holder.binding.btOpenLink.setOnClickListener { view: View? ->
            val i = Intent(Intent.ACTION_VIEW)
            i.setData(Uri.parse(news.link))
            context.startActivity(i)
        }
        // Implementação da funcionalidade de "Compartilhar":
        holder.binding.ivShare.setOnClickListener { view: View? ->
            val i = Intent(Intent.ACTION_SEND)
            i.setType("text/plain")
            i.putExtra(Intent.EXTRA_TEXT, news.link)
            context.startActivity(Intent.createChooser(i, "Share"))
        }
        // Implementação da funcionalidade de "Favoritar" (o evento será instanciado pelo Fragment):
        holder.binding.ivFavorite.setOnClickListener { view: View? ->
            news.favorite = !news.favorite
            favoriteListener.onFavorite(news)
            notifyItemChanged(position)
        }
        val favoriteColor: Int =
            if (news.favorite) R.color.favorite_active else R.color.favorite_inactive
        holder.binding.ivFavorite.setColorFilter(context.resources.getColor(favoriteColor))
    }

    override fun getItemCount(): Int {
        return news.size
    }

    class ViewHolder(private val binding: NewsItemBinding) : RecyclerView.ViewHolder(
        binding.root
    )

    interface FavoriteListener {
        fun onFavorite(news: News?)
    }

    init {
        this.news = news
        this.favoriteListener = favoriteListener
    }
}