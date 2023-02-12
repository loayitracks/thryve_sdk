package com.thryve.sample.cardio

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.thryve.connector.cardio.CardioConnector
import com.thryve.connector.cardio.ICardioConnector
import com.thryve.connector.sdk.logger.Logger
import com.thryve.sample.R
import kotlinx.android.synthetic.main.activity_camera.*

abstract class CameraActivity(connector: CardioConnector? = null) : AppCompatActivity(), ICardioConnector {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        hrvStopRecognitionButton.isEnabled = false

        connector = CardioConnector(this)

        connector?.connectorDelegate = this

        connector?.requestCamera2Access()
        // camera2 works with button, camera does not, because it requires to be launched with the activity
        // try not to use it both at the same time, it causes crashes

        hrvStartRecognitionButton.setOnClickListener {
            connector?.openCamera2(this, textureView)
            hrvStartRecognitionButton.isEnabled = false
            hrvStopRecognitionButton.isEnabled = true
        }

        hrvStopRecognitionButton.setOnClickListener {
            connector?.closeCamera2()
            hrvStartRecognitionButton.isEnabled = true
            hrvStopRecognitionButton.isEnabled = false
        }
    }

    override fun onBackPressed() {
        connector?.closeCamera2()
        super.onBackPressed()
    }

    override fun onStop() {
        connector?.closeCamera2()
        super.onStop()
    }

    override fun onPause() {
        connector?.closeCamera2()
        super.onPause()
    }

    override fun onDestroy() {
        connector?.closeCamera2()
        super.onDestroy()
    }

    /**
     * IHRVConnector methods
     */
    override fun onMeasurementsReceived(heartRate: Long, rmssd: Double) {
        if (heartRate > 0 && rmssd > 0.0) {
            showToast("Success, heart rate: $heartRate, rmmsd: $rmssd") {
                hrvStartRecognitionButton.isEnabled = true
                hrvStopRecognitionButton.isEnabled = false
            }
            Logger.i(this::class.java.simpleName, "Success, heart rate: $heartRate, rmmsd: $rmssd")
        }
    }

    override fun onMeasurementFailed(e: Exception) {
        if (e.message!!.contains("30 seconds")) {
            showToast("Measurement failed: ${e.message}") {
                hrvStartRecognitionButton.isEnabled = true
                hrvStopRecognitionButton.isEnabled = false
            }
        }
        Logger.w(this::class.java.simpleName, "Measurement failed: $e")
    }

    fun AppCompatActivity.showToast(text: String, completion: () -> Unit) {
        runOnUiThread {
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
            completion()
        }
    }
}