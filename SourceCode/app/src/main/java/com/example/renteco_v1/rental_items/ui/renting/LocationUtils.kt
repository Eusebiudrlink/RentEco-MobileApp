package com.example.renteco_v1.rental_items.ui.renting

import android.content.Context
import android.content.Intent
import android.net.Uri

fun openGoogleMaps(context: Context, latitude: Double, longitude: Double) {
    val uri = "http://maps.google.com/maps?q=loc:$latitude,$longitude"
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
    intent.setPackage("com.google.android.apps.maps")
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    context.startActivity(intent)
}

fun openGoogleMapsChargingStations(context: Context) {
    val latitude = 44.4268 // Latitudinea locației tale
    val longitude = 26.1025 // Longitudinea locației tale
    val zoomLevel = 15 // Nivelul de zoom al hărții
    val gmmIntentUri = Uri.parse("geo:$latitude,$longitude?q=charging+stations")
    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
    mapIntent.setPackage("com.google.android.apps.maps")
    mapIntent.putExtra("zoom", zoomLevel)
    context.startActivity(mapIntent)
}
