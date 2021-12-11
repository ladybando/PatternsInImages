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
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.android.patternsinimages.databinding.FragmentImageBinding
/* Fragment that opens camera, and saves image
 * to ImageView for labelling in [ImageAnalyzerFragment].
 * Clicking Image moves to analyzer.
 * */

//todo for animations: https://levelup.gitconnected.com/android-recyclerview-animations-in-kotlin-1e323ffd39be
//todo follow codelab to show label info in image: https://codelabs.developers.google.com/mlkit-android-odt#1
class ImageFragment : Fragment() {
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val imageBitmap = data!!.extras!!.get("data") as Bitmap
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == AppCompatActivity.RESULT_OK) {
           binding.capturedImageView.setImageBitmap(imageBitmap)
        }
        binding.capturedImageView.setOnClickListener {
            val action = ImageFragmentDirections.actionImageFragmentToImageAnalyzerFragment(imageBitmap)
            findNavController().navigate(action)
        }
    }

    private fun openCamera(){
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } catch (e: ActivityNotFoundException) {
            Log.i("ImageFrag1","Error message: $e")
        }
    }
//todo figure out how to use https://www.mongodb.com/developer/article/realm-startactivityforresult-registerForActivityResult-deprecated-android-kotlin/
    private val getResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode ==  Activity.RESULT_OK){
            val value = it.data!!.getStringExtra("data")
        }
    }
}