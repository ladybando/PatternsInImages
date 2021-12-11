package com.example.android.patternsinimages

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

class ImageAnalyzerFragment : Fragment() {
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

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = binding.labelListView
        //pass in argument set as Bitmap in nav to InputImage() method
        val bitmapImage = args.bitmapImage
        val image = InputImage.fromBitmap(bitmapImage, 0)
        val labeler = ImageLabeling.getClient(ImageLabelerOptions.DEFAULT_OPTIONS)
        var text = ""
        var confidence = 0f
//todo figure out how to make this show in recyclerview. Once onclicklistener happens from imagefragment and moves here
        labeler.process(image)
            .addOnSuccessListener { labels ->
                    val adapter = ImageAnalyzerAdapter(labels)
                    recyclerView.adapter = adapter
                    Log.i("ImageFrag", "LOOP>     [$text]:$confidence")
            }
            .addOnFailureListener { e -> Log.d("ImageError", "Error with labeling: $e") }

    }

}