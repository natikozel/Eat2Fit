package com.example.finalproj.util

import android.content.Context
import android.util.Log
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.tasks.await

class BarcodeScanner(
    appContext: Context
) {

    private val options = GmsBarcodeScannerOptions.Builder()
        .setBarcodeFormats(
            Barcode.FORMAT_ALL_FORMATS
        )
        .build()

    private val scanner = GmsBarcodeScanning.getClient(appContext, options)
    val barCodeResults = MutableStateFlow<String?>(null)

    suspend fun startScan(
        popBack: () -> Boolean,
    ) {
        val result = scanner.startScan()
            .addOnSuccessListener {
            barCodeResults.value = it.rawValue
        }.addOnCanceledListener {
            Log.w("BarcodeScanner", "Scan was canceled")
            popBack()
        }.addOnFailureListener { exception ->
            Log.e("BarcodeScanner", "Scan failed", exception)
            popBack()
            // Handle errors
        }.await()
    }


}