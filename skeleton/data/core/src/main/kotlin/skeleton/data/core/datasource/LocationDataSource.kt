package skeleton.data.core.datasource

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority.PRIORITY_HIGH_ACCURACY
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import skeleton.model.core.LatLong
import javax.inject.Inject

class LocationDataSource @Inject constructor(@ApplicationContext private val context: Context) {
    private val provider: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }

    suspend fun getCurrentLocation(): LatLong {
        val isCoarseLocation = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION,
        ) == PackageManager.PERMISSION_GRANTED
        if (!isCoarseLocation) throw Exception("need permission")
        val location =
            provider.getCurrentLocation(
                PRIORITY_HIGH_ACCURACY,
                object : CancellationToken() {
                    override fun onCanceledRequested(p0: OnTokenCanceledListener) =
                        CancellationTokenSource().token

                    override fun isCancellationRequested() = false
                },
            ).await()
        return LatLong(location.latitude, location.longitude)
    }

    suspend fun getLastLocation(default: LatLong): LatLong {
        val isCoarseLocation = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION,
        ) == PackageManager.PERMISSION_GRANTED
        if (!isCoarseLocation) throw Exception("need permission")

        return try {
            val location = provider.lastLocation.await()
            if (location != null) {
                LatLong(location.latitude, location.longitude)
            } else {
                default
            }
        } catch (e: Exception) {
            default
        }
    }
}
