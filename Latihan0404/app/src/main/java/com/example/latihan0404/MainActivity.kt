package com.example.latihan0404

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //instance button
        val button:Button = findViewById(R.id.button)

        //event button next klik
        button.setOnClickListener {
            //explicit intent
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }
    }
}