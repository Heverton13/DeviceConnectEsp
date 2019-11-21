package com.example.deviceconnectesp.scan

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.deviceconnectesp.R
import com.example.deviceconnectesp.SampleApplication
import com.example.deviceconnectesp.util.isLocationPermissionGranted
import com.example.deviceconnectesp.util.requestLocationPermission
import com.polidea.rxandroidble2.exceptions.BleScanException
import com.polidea.rxandroidble2.scan.ScanFilter
import com.polidea.rxandroidble2.scan.ScanSettings

class BackgroundScanActivity : AppCompatActivity() {

    companion object {
        fun newInstance(context: Context) = Intent(context, BackgroundScanActivity::class.java)
    }

    private val rxBleClient = SampleApplication.rxBleClient

    private lateinit var callbackIntent: PendingIntent

    private var hasClickedScan = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_background_scan)
    }

    private fun onScanStartClick() {
        if (isLocationPermissionGranted()) {
            scanBleDeviceInBackground()
        } else {
            hasClickedScan = true
            requestLocationPermission()
        }
    }

    private fun scanBleDeviceInBackground() {
        if (Build.VERSION.SDK_INT >= 26 /* Build.VERSION_CODES.O */) {
            try {
                val scanSettings = ScanSettings.Builder()
                    .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
                    .setCallbackType(ScanSettings.CALLBACK_TYPE_ALL_MATCHES)
                    .build()

                val scanFilter = ScanFilter.Builder()
//                    .setDeviceAddress("5C:31:3E:BF:F7:34")
                    // add custom filters if needed
                    .build()

                rxBleClient.backgroundScanner.scanBleDeviceInBackground(callbackIntent, scanSettings, scanFilter)
            } catch (scanException: BleScanException) {
                Log.e("BackgroundScanActivity", "Failed to start background scan", scanException)
                //showError(scanException)
            }
        } else {
            //showSnackbarShort("Background scanning requires at least API 26")
            Log.i("Erro","Background scanning requires at least API 26" )
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (isLocationPermissionGranted(requestCode, grantResults) && hasClickedScan) {
            hasClickedScan = false
            scanBleDeviceInBackground()
        }
    }

    private fun onScanStopClick() {
        if (Build.VERSION.SDK_INT >= 26 /* Build.VERSION_CODES.O */) {
            rxBleClient.backgroundScanner.stopBackgroundBleScan(callbackIntent)
        } else {
            //showSnackbarShort("Background scanning requires at least API 26")
            Log.i("Erro","Background scanning requires at least API 26" )
        }
    }
}
