package com.thryve.sample.cardio

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.thryve.connector.cardio.CardioConnector
import com.thryve.connector.cardio.ICardioConnector
import com.thryve.connector.sdk.logger.Logger
import com.thryve.sample.R
import kotlinx.android.synthetic.main.activity_control.*

abstract class ControlActivity(connector: CardioConnector? = null) : AppCompatActivity(), ICardioConnector {

    companion object {
        const val TAG = "ControlActivity"
    }

    override fun onMeasurementsReceived(heartRate: Long, rmssd: Double) {
        Logger.i(this::class.java.simpleName, "SENSOR SUCCESS, $heartRate, $rmssd")
        showToast("Measurement success: $heartRate, $rmssd") {}
    }

    override fun onMeasurementFailed(e: Exception) {
        Logger.e(this::class.java.simpleName, "SENSOR ERROR: ${e.message}")
        showToast("Measurement failed: ${e.message}") {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_control)
        val connector = CardioConnector(this)
        connector.connectorDelegate = this
        val address = intent.getStringExtra(CardioConnector.INTENT_EXTRA_DEVICE_ADDRESS)
        connectButton.setOnClickListener {
            address?.let {
                connector.connectBLE(this, it)
            }
        }
        disconnectButton.setOnClickListener {
            connector.disconnectBLE()
        }
    }

    fun AppCompatActivity.showToast(text: String, completion: () -> Unit) {
        runOnUiThread {
            Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
            completion()
        }
    }
}
