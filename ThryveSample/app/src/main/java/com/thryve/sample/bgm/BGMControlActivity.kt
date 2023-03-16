package com.thryve.sample.bgm

import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGattCharacteristic
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.thryve.connector.bgm.BGMConnector
import com.thryve.connector.bgm.ble.IBLE
import com.thryve.connector.sdk.core.Source
import com.thryve.connector.sdk.logger.Logger
import com.thryve.sample.R
import kotlinx.android.synthetic.main.activity_bgmcontrol.*

class BGMControlActivity : AppCompatActivity(), IBLE {

    private val devices: MutableList<BluetoothDevice> = mutableListOf()

    private var connector: BGMConnector<BGMControlActivity>? = null

    private var racp: BluetoothGattCharacteristic? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bgmcontrol)
        val source = (com.thryve.connector.bgm.model.BGMDevice.Companion::initWith)(intent.getIntExtra(BGMActivity.SELECTED_SOURCE, 0))
        connector = BGMConnector(this, source)
        deviceList.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val device = devices[position]
            connector?.connect(device)
        }

        startScanButton.setOnClickListener {
            connector?.startScanning()
        }

        stopScanButton.setOnClickListener {
            connector?.stopScanning()
        }

        disconnectButton.setOnClickListener {
            connector?.delete(source)
        }

        syncButton.isEnabled = false
        syncButton.setOnClickListener {
            racp?.let {
                connector?.sync(it)
            }
        }
        title = source.name
    }

    override fun bleActivated(isOn: Boolean) {
        if (isOn) {
            connector?.startScanning()
        }
    }

    override fun bleFound(device: BluetoothDevice, rssi: Int) {
        devices.forEach {
            if (it.address == device.address) return
        }
        println(device)
        devices.add(device)

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, devices.map { "${it.address} ${it.name}" })
        adapter.setNotifyOnChange(true)
        deviceList.adapter = adapter
    }

    override fun blePaired(device: BluetoothDevice) {
        runOnUiThread {
            Toast.makeText(this, "Device paired", Toast.LENGTH_SHORT).show()
        }
        connector?.disconnect()
    }

    override fun bleReady(racpCharacteristic: BluetoothGattCharacteristic) {
        racp = racpCharacteristic
        runOnUiThread {
            syncButton.isEnabled = true
        }
    }

    override fun bleDisconnected(device: BluetoothDevice) {
        runOnUiThread {
            Toast.makeText(this, "Device disconnected", Toast.LENGTH_SHORT).show()
        }
    }

    override fun bleException(error: Exception) {
        Logger.e("BLE", "${error.message}")
    }


}