package com.skripsi.testapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.skripsi.testapp.databinding.ItemBinding

class ListAdapter(private val diseases: List<Disease>, private val onItemClick: (Disease) -> Unit) :
    RecyclerView.Adapter<ListAdapter.DiseaseViewHolder>() {

    class DiseaseViewHolder(val binding: ItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiseaseViewHolder {
        val binding = ItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DiseaseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DiseaseViewHolder, position: Int) {
        val disease = diseases[position]
        with(holder.binding) {
            ivDiseaseImage.setImageResource(disease.imageResId)
            tvDiseaseName.text = disease.diseaseName
            tvDiseaseLatin.text = disease.diseaseLatin
        }

        holder.itemView.setOnClickListener {
            onItemClick(disease)
        }
    }

    override fun getItemCount(): Int = diseases.size
}
