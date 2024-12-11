package com.dicoding.capstone.ui.home

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.dicoding.capstone.CameraActivity
import com.dicoding.capstone.CameraActivity.Companion.CAMERAX_RESULT
import com.dicoding.capstone.R
import com.dicoding.capstone.data.api.AnalysisResult
import com.dicoding.capstone.data.api.ApiConfig
import com.dicoding.capstone.databinding.FragmentMainBinding
import com.dicoding.capstone.getImageUri
import com.dicoding.capstone.reduceFileImage
import com.dicoding.capstone.ui.viewmodel.AnalysisResultViewModel
import com.dicoding.capstone.uriToFile
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.HttpException

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val analysisResults = mutableListOf<AnalysisResult>()
    private var currentImageUri: Uri? = null
    private lateinit var analysisResultViewModel: AnalysisResultViewModel

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(requireContext(), "Permission granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(requireContext(), "Permission denied", Toast.LENGTH_LONG).show()
            }
        }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            requireContext(),
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        analysisResultViewModel = ViewModelProvider(requireActivity())[AnalysisResultViewModel::class.java]


        if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

        binding.galleryButton.setOnClickListener { startGallery() }
        binding.cameraButton.setOnClickListener { startCamera() }
        binding.cameraXButton.setOnClickListener { startCameraX() }
        binding.uploadButton.setOnClickListener { uploadImage() }

        return binding.root
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        uri?.let {
            currentImageUri = it
            showImage()
        } ?: Log.d("Photo Picker", "No media selected")
    }

    private fun startCamera() {
        val uri = getImageUri(requireContext())
        currentImageUri = uri
        launcherIntentCamera.launch(uri)
    }

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.TakePicture()
    ) { isSuccess ->
        if (isSuccess) showImage()
    }

    private fun startCameraX() {
        val intent = Intent(requireContext(), CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERAX_RESULT) {
            currentImageUri = it.data?.getStringExtra(CameraActivity.EXTRA_CAMERAX_IMAGE)?.toUri()
            showImage()
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            binding.previewImageView.setImageURI(it)
        }
    }

    @SuppressLint("DefaultLocale")
    private fun uploadImage() {
        currentImageUri?.let { uri ->
            val imageFile = uriToFile(uri, requireContext()).reduceFileImage()
            val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
            val multipartBody = MultipartBody.Part.createFormData(
                "file", imageFile.name, requestImageFile
            )

            showLoading(true)
            lifecycleScope.launch {
                try {
                    val apiService = ApiConfig.instance2
                    val response = apiService.uploadImage(multipartBody)

                    val prediction = response.prediction
                    val status = response.status

                    if (status == "success") {
                        val predictionValue = prediction ?: 0f
                        val predictionText = String.format("Tingkat Stress: %.2f", predictionValue * 100) + "%"

                        // Simpan hasil ke daftar
                        analysisResults.add(AnalysisResult(uri.toString(), predictionText))
                        Toast.makeText(requireContext(), "Hasil disimpan!", Toast.LENGTH_SHORT).show()

                        // Kirim hasil ke ViewModel untuk dibagikan dengan ProfileFragment
                        analysisResultViewModel.addResult(AnalysisResult(uri.toString(), predictionText))

                        // Navigasi ke ProfileFragment
                        findNavController().navigate(R.id.nav_profile)
                    } else {
                        showToast("Upload failed!")
                    }
                } catch (e: HttpException) {
                    val errorBody = e.response()?.errorBody()?.string() ?: "Unknown error"
                    showToast("Error: $errorBody")
                } catch (e: Exception) {
                    showToast("Unexpected error: ${e.localizedMessage}")
                } finally {
                    showLoading(false)
                }
            }
        } ?: showToast(getString(R.string.image_empty))
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressIndicator.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
    }
}
