package com.dicoding.capstone.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.capstone.data.api.AnalysisResult


class AnalysisResultViewModel : ViewModel() {
    // Gunakan LiveData untuk menyimpan dan mengamati data secara reaktif
    private val _results = MutableLiveData<List<AnalysisResult>>()
    val results: LiveData<List<AnalysisResult>> get() = _results

    fun addResult(result: AnalysisResult) {
        val currentResults = _results.value?.toMutableList() ?: mutableListOf()
        currentResults.add(result)
        _results.value = currentResults
    }
}
