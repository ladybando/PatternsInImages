package com.example.android.patternsinimages

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.android.patternsinimages.databinding.FragmentImageBinding

/* Fragment that opens camera, and saves image
 * to ImageView for labelling in [ImageAnalyzerFragment].
 * Clicking Image moves to analyzer.
 * */

class ImageFragment : Fragment() {
//todo find replacement for onActivityResult: https://www.google.com/search?q=super.onactivityresult+deprecated&rlz=1C1CHBF_enUS947US947&sxsrf=AOaemvKrlrrEw8u9VeM9QbVK4IbqjbiXEg%3A1638838114659&ei=Yq-uYabSJ96iptQPucCo8AI&oq=super.onActivityResult+depre&gs_lcp=Cgdnd3Mtd2l6EAEYADIFCAAQgAQ6BwgjELADECc6BwgAEEcQsAM6BAgjECc6BggAEBYQHkoECEEYAFCYBliGDWC5GmgBcAJ4AIABZ4gB4QSSAQM1LjKYAQCgAQHIAQnAAQE&sclient=gws-wiz
    //todo: set labels to recycler view
    private var _binding: FragmentImageBinding?= null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentImageBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cameraButton.setOnClickListener { openCamera() }
    }

    private val getResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) {
        val imageBitmap = it.data!!.extras!!.get("data") as Bitmap
        if (it.resultCode == Activity.RESULT_OK) {
            binding.capturedImageView.setImageBitmap(imageBitmap)
        }
        binding.capturedImageView.setOnClickListener {
            val action =
                ImageFragmentDirections.actionImageFragmentToImageAnalyzerFragment(imageBitmap)
            findNavController().navigate(action)
        }
    }

    private fun openCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            getResult.launch(takePictureIntent)
        } catch (e: ActivityNotFoundException) {
            Log.i("ImageFrag1", "Error message: $e")
        }
    }
}