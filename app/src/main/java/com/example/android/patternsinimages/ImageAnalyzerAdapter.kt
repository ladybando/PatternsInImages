package com.example.android.patternsinimages

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.get
import androidx.recyclerview.widget.RecyclerView
import com.example.android.patternsinimages.databinding.ImageAnalyzerLayoutBinding
import com.google.mlkit.vision.label.ImageLabel

class ImageAnalyzerAdapter(private val dataSet: MutableList<ImageLabel>) :
    RecyclerView.Adapter<ImageAnalyzerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ImageAnalyzerLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int): Unit = with(holder) {
        binding.objectTrackLabelView.text = dataSet[position].text
        binding.objectTrackConfidenceView.text = dataSet[position].confidence.toString()
    }

    override fun getItemCount(): Int = dataSet.size

    inner class ViewHolder(val binding: ImageAnalyzerLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)
}