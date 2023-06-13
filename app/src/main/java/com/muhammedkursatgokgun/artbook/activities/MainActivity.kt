package com.muhammedkursatgokgun.artbook.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
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
        var email = binding.edtTextEMail.text.toString()
        var password = binding.edtTextPassword.text.toString()
        if(email.isNotEmpty()&&password.isNotEmpty()){
            auth.signInWithEmailAndPassword(email,password).addOnSuccessListener {
                val intent= Intent(this,ArtActivity::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener {
                Toast.makeText(this,it.localizedMessage,Toast.LENGTH_LONG).show()
            }
        }else{
            Toast.makeText(this,"Enter password and email",Toast.LENGTH_LONG).show()
        }

    }
    fun signUp(view:View){
        var email = binding.edtTextEMail.text.toString()
        var password = binding.edtTextPassword.text.toString()
        if(email.isNotEmpty()&&password.isNotEmpty()) {
            auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
                val intent = Intent(this, ArtActivity::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener {
                Toast.makeText(this, it.localizedMessage, Toast.LENGTH_LONG).show()
            }
        }else{
            Toast.makeText(this,"Enter password and email",Toast.LENGTH_LONG).show()
        }
    }
}