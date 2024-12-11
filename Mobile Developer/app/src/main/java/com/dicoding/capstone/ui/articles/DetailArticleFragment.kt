package com.dicoding.capstone.ui.articles

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.text.HtmlCompat
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.dicoding.capstone.R
import com.dicoding.capstone.data.response.Article
import com.google.android.material.bottomnavigation.BottomNavigationView

@Suppress("DEPRECATION")
class DetailArticleFragment : Fragment() {
    private lateinit var progressBar: ProgressBar
    private lateinit var imageView: ImageView
    private lateinit var titleView: TextView
    private lateinit var descriptionView: TextView
    private lateinit var sourceView: TextView
    private lateinit var dateView: TextView
    private lateinit var toolbar: Toolbar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detail_article, container, false)
        // Inisialisasi view
        progressBar = view.findViewById(R.id.progress_bar)
        imageView = view.findViewById(R.id.ivMediaCover)
        titleView = view.findViewById(R.id.tvName)
        descriptionView = view.findViewById(R.id.tvDescription)
        sourceView = view.findViewById(R.id.tvSource)
        dateView = view.findViewById(R.id.tvDate)
        toolbar = view.findViewById(R.id.toolbar)

        // Setup Toolbar
        toolbar.setNavigationIcon(R.drawable.ic_back_1)
        toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        // Sembunyikan BottomNavigationView
        val bottomNav = activity?.findViewById<BottomNavigationView>(R.id.mobile_navigation)
        bottomNav?.visibility = View.GONE

        // Ambil data artikel
        val article = arguments?.getParcelable<Article>("article")
        if (article != null) {
            displayArticleDetail(article)
        } else {
            Toast.makeText(context, "Article data is missing", Toast.LENGTH_SHORT).show()
        }

        return view
    }

    private fun displayArticleDetail(article: Article) {
        progressBar.visibility = View.VISIBLE
        try {
            titleView.text = article.title ?: "No Title"
            descriptionView.text = article.content ?: "No Content"
            dateView.text = "Date: ${article.date ?: "No Date"}"

            if (!article.source.isNullOrEmpty() && !article.source.isNullOrEmpty()) {
                // Buat teks dengan format: "Source: [Nama Sumber] (URL)"
                val sourceText = "Source: ${article.source}"
                val spannable = SpannableString(sourceText)

                // Temukan posisi URL dalam teks
                val urlStartIndex = sourceText.indexOf(article.source)
                val urlEndIndex = urlStartIndex + article.source.length

                spannable.setSpan(
                    ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.blue)), // Ganti dengan warna biru Anda
                    urlStartIndex,
                    urlEndIndex,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )

                // Tambahkan klik pada URL
                spannable.setSpan(
                    object : ClickableSpan() {
                        override fun onClick(widget: View) {
                            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(article.source))
                            startActivity(browserIntent)
                        }
                    },
                    urlStartIndex,
                    urlEndIndex,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )

                sourceView.text = spannable
                sourceView.movementMethod = LinkMovementMethod.getInstance() // Aktifkan klik pada link
            } else {
                sourceView.text = "Source: ${article.source ?: "Unknown"}"
            }

            // Muat gambar menggunakan Glide
            Glide.with(imageView.context)
                .load(article.imageUrl ?: R.drawable.logocapstone)
                .into(imageView)
        } catch (e: Exception) {
            Log.e("DetailArticleFragment", "Error displaying article detail", e)
            Toast.makeText(context, "Failed to load article detail", Toast.LENGTH_SHORT).show()
        } finally {
            progressBar.visibility = View.GONE
        }
    }
}
