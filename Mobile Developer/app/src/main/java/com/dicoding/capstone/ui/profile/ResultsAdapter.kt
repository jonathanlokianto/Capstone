package com.dicoding.capstone.ui.profile

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.capstone.R
import com.dicoding.capstone.data.api.AnalysisResult

class ResultsAdapter(
    private val results: MutableList<AnalysisResult>, // Ubah ke MutableList agar bisa dimodifikasi
    private val onDeleteClick: (Int) -> Unit // Tambahkan listener untuk tombol delete
) : RecyclerView.Adapter<ResultsAdapter.ResultViewHolder>() {

    inner class ResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.ivMediaStress)
        val textView: TextView = itemView.findViewById(R.id.tvCardPrediction)
        val deleteButton: ImageButton = itemView.findViewById(R.id.btnDelete) // Tombol delete

        init {
            // Set listener untuk tombol delete
            deleteButton.setOnClickListener {
                onDeleteClick(adapterPosition) // Panggil listener untuk menghapus item
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_result_card, parent, false)
        return ResultViewHolder(view)
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        val result = results[position]
        holder.imageView.setImageURI(Uri.parse(result.imageUri)) // Set gambar
        holder.textView.text = result.predictionText // Set prediksi
    }

    override fun getItemCount(): Int = results.size

    // Fungsi untuk menghapus item
    fun removeItem(position: Int) {
        results.removeAt(position)
        notifyItemRemoved(position) // Memberi tahu adapter bahwa item dihapus
    }

    // Fungsi untuk memperbarui daftar hasil
    fun updateResults(newResults: List<AnalysisResult>) {
        results.clear()
        results.addAll(newResults)
        notifyDataSetChanged()
    }
}
