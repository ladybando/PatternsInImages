package com.example.android.patternsinimages

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.android.patternsinimages.databinding.FragmentImageAnalyzerBinding
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.label.ImageLabeling
import com.google.mlkit.vision.label.defaults.ImageLabelerOptions

class ImageAnalyzerFragment : Fragment(), ImageAnalyzerAdapter.Listener {
    private var _binding: FragmentImageAnalyzerBinding? = null
    private val binding get() = _binding!!
    private  val args: ImageAnalyzerFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentImageAnalyzerBinding.inflate(inflater, container, false)
        //pass in argument set as Bitmap in nav to analyze() method
        val bitmapImage = args.bitmapImage
        analyze(bitmapImage)
        return binding.root
    }

    private fun analyze(imageBitmap: Bitmap) {
        val image = InputImage.fromBitmap(imageBitmap, 0)
        val labeler = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS)
        var text = ""
        var confidence = 0f
//todo figure out how to make this show in recyclerview. Once onclicklistener happens from imagefragment and moves here
        labeler.process(image)
            .addOnSuccessListener { labels ->
                for (label in labels) {
                    text = label.text
                    confidence = label.confidence
                    Log.i("Frank", "LOOP>     [$text]:$confidence")
                }
                binding.objectTrackTextView.text = text
                binding.objectTrackLabelView.text = confidence.toString()
                Log.i("ImageFrag", text + confidence)
            }
            .addOnFailureListener { e -> Log.d("ImageError", "Error with labeling: $e") }
    }

    override fun onTaskClicked(index: Int) {
        TODO("Not yet implemented")
    }

    override fun onLabelClicked(index: Int) {
        TODO("Not yet implemented")
    }

}