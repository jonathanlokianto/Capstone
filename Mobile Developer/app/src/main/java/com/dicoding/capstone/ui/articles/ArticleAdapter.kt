package com.dicoding.capstone.ui.articles

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.capstone.R
import com.dicoding.capstone.data.response.Article

class ArticleAdapter(
    private val articles: List<Article>,
    private val onClick: (Article) -> Unit,
    private val isHorizontal: Boolean
) : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val layoutId = if (isHorizontal) R.layout.article_layout_horizontal else R.layout.article_layout
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = articles[position]
        holder.bind(article)
    }

    override fun getItemCount(): Int = articles.size

    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleView: TextView = itemView.findViewById(R.id.tv_Article_Name)
        private val descriptionView: TextView? = itemView.findViewById(R.id.tvDescription) // Perhatikan nullable
        private val imageView: ImageView = itemView.findViewById(R.id.tv_Article_Picture)
        private val source: TextView? = itemView.findViewById(R.id.tvSource)
        private val date: TextView? = itemView.findViewById(R.id.tvDate)

        fun bind(article: Article) {
            titleView.text = article.title ?: "No Title"
            descriptionView?.text = article.content ?: "No Description Available"
            source?.text = article.source ?: "No Source"
            date?.text = article.date ?: "No Date"// Cek null sebelum akses

            Glide.with(itemView.context)
                .load(article.imageUrl ?: R.drawable.logocapstone) // Placeholder jika URL kosong
                .into(imageView)

            itemView.setOnClickListener { onClick(article) }
        }
    }

}
