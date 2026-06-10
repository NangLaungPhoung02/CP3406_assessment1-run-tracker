package au.edu.jcu.cp3406_cp5307_utilityappstartertemplate

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.LocationServices

data class LocationData(
    val latitude: Double,
    val longitude: Double
)

class LocationUtils(
    private val context: Context
) {

    private val fusedLocationClient =
        LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
    fun getCurrentLocation(
        onLocationReceived: (LocationData?) -> Unit
    ) {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location != null) {
                    onLocationReceived(
                        LocationData(
                            latitude = location.latitude,
                            longitude = location.longitude
                        )
                    )
                } else {
                    onLocationReceived(null)
                }
            }
    }
}