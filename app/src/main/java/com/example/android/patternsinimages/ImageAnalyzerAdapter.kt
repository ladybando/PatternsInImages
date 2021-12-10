package com.example.android.patternsinimages

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.patternsinimages.databinding.ImageAnalyzerLayoutBinding

class ImageAnalyzerAdapter(
    private val dataSet: MutableList<String>,
    private val listener: Listener
) :
    RecyclerView.Adapter<ImageAnalyzerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ImageAnalyzerLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int): Unit = with(holder) {
        binding.objectTrackTextView.text = dataSet[position]
        binding.objectTrackTextView.text = dataSet[position]
    }

    override fun getItemCount(): Int = dataSet.size

    inner class ViewHolder(val binding: ImageAnalyzerLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val textView = binding.objectTrackTextView
        private val labelView = binding.objectTrackLabelView

        init {
            textView.setOnClickListener {
                listener.onTaskClicked(adapterPosition)
            }
            labelView.setOnClickListener {
                listener.onLabelClicked(adapterPosition)
            }
        }
    }

    interface Listener {
        fun onTaskClicked(index: Int)
        fun onLabelClicked(index: Int)
    }
}