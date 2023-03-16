package com.thryve.sample.cardio

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.thryve.sample.R
import kotlinx.android.synthetic.main.activity_cardio.*

class CardioActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cardio)

        cameraHrvButton.setOnClickListener {
            val intent = Intent(this, CameraActivity::class.java)
            startActivity(intent)
        }

        bleDevicesButton.setOnClickListener {
            val intent = Intent(this, BLEActivity::class.java)
            startActivity(intent)
        }
    }
}
