package com.dicoding.capstone.ui.profile

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dicoding.capstone.R
import com.dicoding.capstone.data.api.AnalysisResult
import com.dicoding.capstone.databinding.FragmentDetailStressBinding
import com.dicoding.capstone.ui.viewmodel.AnalysisResultViewModel

class DetailStressFragment : Fragment() {

    private var _binding: FragmentDetailStressBinding? = null
    private val binding get() = _binding!!
    private lateinit var analysisResultViewModel: AnalysisResultViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDetailStressBinding.inflate(inflater, container, false)



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inisialisasi ViewModel
        analysisResultViewModel = ViewModelProvider(requireActivity()).get(AnalysisResultViewModel::class.java)

        // Ambil data dari arguments
        val predictionResult = arguments?.getString(EXTRA_PREDICTION) ?: "Prediction: -"
        val imageUriString = arguments?.getString(EXTRA_IMAGE_URI) ?: ""

        // Tampilkan hasil analisis
        binding.tvCardPrediction.text = predictionResult

        // Tampilkan gambar di bagian atas jika URI tersedia
        if (!imageUriString.isNullOrEmpty()) {
            try {
                val imageUri = Uri.parse(imageUriString)
                binding.ivMediaStress.setImageURI(imageUri)
            } catch (e: Exception) {
                Log.e("DetailStressFragment", "Error parsing image URI: $imageUriString", e)
                binding.ivMediaStress.setImageResource(R.drawable.ic_place_holder) // Placeholder image
            }
        } else {
            binding.ivMediaStress.setImageResource(R.drawable.ic_place_holder) // Gambar default
        }

        // Kirim data ke ViewModel
        val result = AnalysisResult(predictionResult, imageUriString)
        analysisResultViewModel.addResult(result)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val EXTRA_PREDICTION = "extra_prediction"
        const val EXTRA_IMAGE_URI = "extra_image_uri"
    }
}
