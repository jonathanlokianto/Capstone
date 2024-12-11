package com.dicoding.capstone.ui.home

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.capstone.R
import com.dicoding.capstone.data.api.ApiConfig
import com.dicoding.capstone.ui.articles.ArticleAdapter
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var Button: Button
    private lateinit var imageView: ImageView
    private var isDefaultImage = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        imageView = view.findViewById(R.id.myImageView)
        recyclerView = view.findViewById(R.id.rvArticle)
        progressBar = view.findViewById(R.id.progress_bar)
        Button = view.findViewById(R.id.button) // Tambahkan inisialisasi tombol kamera

        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        // Tambahkan listener pada tombol kamera
        Button.setOnClickListener {
            findNavController().navigate(R.id.action_nav_home_to_mainFragment,
                null,
                NavOptions.Builder().setPopUpTo(R.id.nav_home, true).build())
        }

        fetchArticles()

        imageView.setImageResource(R.drawable.pict_emot)

        // Mulai timer untuk mengganti gambar secara otomatis
        startImageToggleTimer()

        return view
    }

    private fun fetchArticles() {
        progressBar.visibility = View.VISIBLE

        lifecycleScope.launch {
            try {
                val response = ApiConfig.instance1.getArticles()
                val articles = response.data?.filterNotNull() ?: emptyList()

                if (articles.isNotEmpty()) {
                    recyclerView.adapter = ArticleAdapter(
                        articles = articles,
                        onClick = { article ->
                            val bundle = Bundle().apply {
                                putParcelable("article", article)
                            }
                            findNavController().navigate(
                                R.id.action_nav_home_to_nav_detail_article,
                                bundle
                            )
                        },
                        isHorizontal = true
                    )
                } else {
                    Toast.makeText(context, "No articles available", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Log.e("HomeFragment", "Error fetching articles", e)
                Toast.makeText(context, "Failed to load articles", Toast.LENGTH_SHORT).show()
            } finally {
                progressBar.visibility = View.GONE
            }
        }
    }

    private fun toggleImageWithPopAnimation() {
        // Buat animasi skala pop (zoom in and out)
        val scaleUpX = ObjectAnimator.ofFloat(imageView, "scaleX", 1f, 1.2f)
        val scaleUpY = ObjectAnimator.ofFloat(imageView, "scaleY", 1f, 1.2f)
        val scaleDownX = ObjectAnimator.ofFloat(imageView, "scaleX", 1.2f, 1f)
        val scaleDownY = ObjectAnimator.ofFloat(imageView, "scaleY", 1.2f, 1f)

        // Gabungkan animasi dalam AnimatorSet
        val popAnimation = AnimatorSet().apply {
            play(scaleUpX).with(scaleUpY)
            play(scaleDownX).with(scaleDownY).after(scaleUpX)
            duration = 300 // Durasi animasi total
        }

        // Jalankan animasi dan ubah gambar setelah selesai
        popAnimation.start()
        popAnimation.addListener(object : android.animation.AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: android.animation.Animator) {
                // Ubah gambar setelah animasi selesai
                if (isDefaultImage) {
                    imageView.setImageResource(R.drawable.pict_emote1)
                } else {
                    imageView.setImageResource(R.drawable.pict_emot)
                }
                isDefaultImage = !isDefaultImage // Toggle state gambar
            }
        })
    }

    private fun startImageToggleTimer() {
        lifecycleScope.launch {
            while (isActive) { // Coroutine hanya berjalan selama fragment aktif
                toggleImageWithPopAnimation()
                delay(5000) // Tunggu 10 detik
            }
        }
    }

}
