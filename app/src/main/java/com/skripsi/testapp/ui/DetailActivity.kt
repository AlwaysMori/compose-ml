package com.skripsi.testapp.ui

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.skripsi.testapp.R
import com.skripsi.testapp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnBack.setOnClickListener {
            finish()
        }
        val diseaseName = intent.getStringExtra("diseaseName") ?: ""
        val diseaseLatin = intent.getStringExtra("diseaseLatin") ?: ""
        val imageResId = intent.getIntExtra("imageResId", 0)
        val chemicalControl = intent.getStringExtra("chemicalControl") ?: ""
        val traditionalControl = intent.getStringExtra("traditionalControl") ?: ""
        val backButton: ImageButton = findViewById(R.id.btnBack)
        backButton.setOnClickListener {
            onBackPressed()
        }

        binding.ivDiseaseImage.setImageResource(imageResId)
        binding.tvDiseaseName.text = diseaseName
        binding.tvDiseaseLatin.text = diseaseLatin
        binding.tvChemicalControl.text = chemicalControl
        binding.tvTraditionalControl.text = traditionalControl
    }
}

