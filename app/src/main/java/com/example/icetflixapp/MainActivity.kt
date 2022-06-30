package com.example.icetflixapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.icetflixapp.databinding.ActivityMainBinding
import com.example.icetflixapp.databinding.ActivitySingInBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.button2.setOnClickListener{
            firebaseAuth.signOut()

            val navTela = Intent(this, SingInActivity::class.java)
            startActivity(navTela)
        }
    }
}