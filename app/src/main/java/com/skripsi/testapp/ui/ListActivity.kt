package com.skripsi.testapp.ui

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.skripsi.testapp.R
import com.skripsi.testapp.databinding.ActivityListBinding

class ListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val diseases = listOf(
            Disease(
                diseaseName = "Healthy",
                diseaseLatin = "Sehat",
                imageResId = R.drawable.health
            ),
            Disease(
                diseaseName = "Bacterial",
                diseaseLatin = "Bakterial",
                imageResId = R.drawable.bac
            ),
            Disease(
                diseaseName = "Fungal",
                diseaseLatin = "Jamur",
                imageResId = R.drawable.fung
            ),
            Disease(
                diseaseName = "Shepherd's Purse",
                diseaseLatin = "Dompet Gembala",
                imageResId = R.drawable.spw
            )
        )

        binding.rvitem.layoutManager = LinearLayoutManager(this)
        binding.rvitem.adapter = ListAdapter(diseases) { disease ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("diseaseName", disease.diseaseName)
            intent.putExtra("diseaseLatin", disease.diseaseLatin)
            intent.putExtra("imageResId", disease.imageResId)
            intent.putExtra(
                "chemicalControl",
                when (disease.diseaseName) {
                    "Bacterial" -> getString(R.string.penyebab_bacterial)
                    "Fungal" -> getString(R.string.penyebab_fungal)
                    "Shepherd's Purse" -> getString(R.string.penyebab_shepherd)
                    else -> "No data"
                }
            )
            intent.putExtra(
                "traditionalControl",
                when (disease.diseaseName) {
                    "Bacterial" -> getString(R.string.pengendalian_bacterial)
                    "Fungal" -> getString(R.string.pengendalian_fungal)
                    "Shepherd's Purse" -> getString(R.string.pengendalian_shepherd)
                    else -> "No data"
                }
            )
            startActivity(intent)
        }

    }

}

