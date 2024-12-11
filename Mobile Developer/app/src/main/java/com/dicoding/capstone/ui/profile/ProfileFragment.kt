package com.dicoding.capstone.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.capstone.data.api.AnalysisResult
import com.dicoding.capstone.databinding.FragmentProfileBinding
import com.dicoding.capstone.ui.viewmodel.AnalysisResultViewModel

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ResultsAdapter
    private val results = mutableListOf<AnalysisResult>()
    private lateinit var analysisResultViewModel: AnalysisResultViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        analysisResultViewModel = ViewModelProvider(requireActivity()).get(AnalysisResultViewModel::class.java)

        setupRecyclerView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Mengamati perubahan data di ViewModel
        analysisResultViewModel.results.observe(viewLifecycleOwner, { results ->
            // Perbarui daftar hasil
            (adapter as ResultsAdapter).updateResults(results)
        })
    }

    private fun setupRecyclerView() {
        adapter = ResultsAdapter(
            results = mutableListOf(),
            onDeleteClick = { position ->
                // Menghapus item
                adapter.removeItem(position)
                Toast.makeText(requireContext(), "Item Terhapus", Toast.LENGTH_SHORT).show()
            }
        )

        binding.rvStress.layoutManager = LinearLayoutManager(requireContext())
        binding.rvStress.adapter = adapter
    }

    private fun loadResults() {
        // Tambahkan data dummy untuk menguji
        results.addAll(
            listOf(
                AnalysisResult("Prediction: High Stress", "content://media/external/images/media/1"),
                AnalysisResult("Prediction: Low Stress", "content://media/external/images/media/2")
            )
        )

        // Memberitahu adapter bahwa data telah diperbarui
        adapter.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
