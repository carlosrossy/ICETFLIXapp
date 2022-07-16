package com.example.icetflixapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.icetflixapp.databinding.ActivityMainBinding
import com.example.icetflixapp.databinding.ActivitySuccessBinding

class SuccessActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySuccessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySuccessBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}