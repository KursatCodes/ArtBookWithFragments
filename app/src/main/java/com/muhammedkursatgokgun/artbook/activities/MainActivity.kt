package com.muhammedkursatgokgun.artbook.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.muhammedkursatgokgun.artbook.R
import com.muhammedkursatgokgun.artbook.databinding.ActivityMainBinding

private lateinit var auth: FirebaseAuth
private lateinit var binding: ActivityMainBinding
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        auth = Firebase.auth

        val currentUser = auth.currentUser
        if (currentUser != null){
            val intent= Intent(this,ArtActivity::class.java)
            startActivity(intent)
        }

    }
    fun signIn(view:View){
<<<<<<< HEAD
        val email=2
=======
        var email = binding.edtTextEMail.text.toString()
        var password = binding.edtTextPassword.text.toString()

>>>>>>> 60617c8048b9a373a633000945f7c235708dd2a1
    }
    fun signUp(view:View){

    }
}