package com.example.icetflixapp

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.icetflixapp.databinding.ActivityRecoverPasswordBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class RecoverPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecoverPasswordBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecoverPasswordBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.btnEnviar.setOnClickListener{
            val email = binding.inputEmail.text.toString()

            if(email.isNotEmpty()){
                firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener{
                    if(it.isSuccessful){
                        val snackbar= Snackbar.make(view,"Verifique seu E-mail", Snackbar.LENGTH_SHORT)
                        snackbar.setBackgroundTint(Color.GREEN)
                        snackbar.show()
                    }else{
                        val snackbar= Snackbar.make(view,"Ocorreu um erro", Snackbar.LENGTH_SHORT)
                        snackbar.setBackgroundTint(Color.RED)
                        snackbar.show()
                    }
                }
            }else{
                val snackbar= Snackbar.make(view,"informe seu E-mail", Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.show()
            }
        }
    }
}