package com.example.deviceconnectesp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

private const val EXTRA_MAC_ADDRESS = "extra_mac_address"

class MainActivity : AppCompatActivity() {

    companion object {
        fun newInstance(context: Context, macAddress: String): Intent =
            Intent(context, MainActivity::class.java).apply { putExtra(EXTRA_MAC_ADDRESS, macAddress) }
    }

    private lateinit var macAddress: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        macAddress = intent.getStringExtra(EXTRA_MAC_ADDRESS)
        //supportActionBar!!.subtitle = getString(R.string.mac_address, macAddress)

        //connect.setOnClickListener { startActivity(ConnectionExampleActivity.newInstance(this, macAddress)) }
        //discovery.setOnClickListener { startActivity(ServiceDiscoveryExampleActivity.newInstance(this, macAddress)) }
    }
}
