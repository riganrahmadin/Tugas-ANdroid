package com.example.latihan0404

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        imgbuttonCall.setOnClickListener{
            val callIntent = Uri.parse("tel:0857439096").Let {number ->
                Intent(Intent.Action_Dial,number)
            }
            startActivity(callIntent)
        }
        imgButtonMap.setOnClickListener{
            val mapIntent = Intent(Intent(.ACTION_VIEW).apply{
                data = Uri.parse("ge0:47.6-122.3?z=11")
            }
            if (mapIntent.resoslveActivity(packageManager)!=null){
                startActivity(mapIntent)
            }
        }
        val imgButtonWeb.setOnclickListener{
            val wrbIntent:Intent = Uri.parse("")
        }

    }
}