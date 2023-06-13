package com.muhammedkursatgokgun.artbook.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.muhammedkursatgokgun.artbook.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intent= Intent(this,ArtActivity::class.java)
        startActivity(intent)
    }
    fun signIn(view:View){
        val email=2
    }
    fun signUp(view:View){

    }
}