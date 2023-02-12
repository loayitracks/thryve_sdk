package com.thryve.sample.bgm

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.thryve.connector.bgm.model.BGMDevice
import com.thryve.sample.R
import kotlinx.android.synthetic.main.activity_bgm.*

class BGMActivity : AppCompatActivity() {

    companion object {
        const val SELECTED_SOURCE: String = "SELECTED_SOURCE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bgm)
        title = "List of supported sources"

        val sources = listOf("I-Sens", "BBraun", "RocheAccuchek")

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, sources)
        supportedSourcesList.adapter = adapter

        supportedSourcesList.setOnItemClickListener { _, _, i, _ ->
            val intent = Intent(this, BGMControlActivity::class.java)
            when (i) {
                0 -> intent.putExtra(SELECTED_SOURCE, BGMDevice.ISENS.id)
                1 -> intent.putExtra(SELECTED_SOURCE, BGMDevice.BBRAUN.id)
                2 -> intent.putExtra(SELECTED_SOURCE, BGMDevice.ROCHE_ACCUCHEK.id)
            }
            this.startActivity(intent)
        }
    }
}
