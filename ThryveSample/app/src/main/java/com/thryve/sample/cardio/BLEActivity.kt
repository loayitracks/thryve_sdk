package com.thryve.sample.cardio

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.thryve.connector.cardio.CardioConnector
import com.thryve.connector.cardio.ICardioConnector
import com.thryve.sample.R
import kotlinx.android.synthetic.main.activity_cardio_ble.*

abstract class BLEActivity(connector: CardioConnector? = null) : AppCompatActivity(), ICardioConnector {

    override fun onMeasurementsReceived(heartRate: Long, rmssd: Double) {
        runOnUiThread {
            Toast.makeText(this, "Success, heart rate: $heartRate, rmmsd: $rmssd", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onMeasurementFailed(e: Exception) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cardio_ble)

        connector = CardioConnector(this)

        startScanButton.setOnClickListener {
            connector!!.startScanBLE(this)
        }

        deviceRefreshButton.setOnClickListener {
            pairedDeviceList()
        }
    }

    override fun onDestroy() {
        connector?.stopScanBLE()
        super.onDestroy()
    }

    @SuppressLint("MissingPermission")
    private fun pairedDeviceList() {
        connector?.getBLEDeviceList()?.let { devices ->
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, devices.map { "${it.address} ${it.name}" })
            deviceList.adapter = adapter
            deviceList.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
                connector?.stopScanBLE()
                val device = devices[position]
                val address = device.address
                val intent = Intent(this, ControlActivity::class.java)
                intent.putExtra(CardioConnector.INTENT_EXTRA_DEVICE_ADDRESS, address)
                startActivity(intent)
            }
        }
    }
}
