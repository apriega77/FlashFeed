package skeleton.data.core.repository

import android.util.Log
import base.data.BasePreference
import kotlinx.coroutines.withTimeout
import skeleton.abstraction.core.LocationRepository
import skeleton.data.core.datasource.LocationDataSource
import skeleton.model.core.LatLong
import skeleton.model.core.LocationPreferenceKey
import javax.inject.Inject

class LocationRepositoryImpl @Inject constructor(
    private val locationDataSource: LocationDataSource,
    private val basePreference: BasePreference,
) : LocationRepository {
    override suspend fun fetchCurrentLocation(): LatLong {
        var location: LatLong? = null
        try {
            location = withTimeout(1000) {
                locationDataSource.getCurrentLocation()
            }
            basePreference.putData(LocationPreferenceKey, location)
        } catch (i: IllegalArgumentException) {
            Log.i("Failed get location", i.toString())
        } catch (s: SecurityException) {
            Log.i("Failed get location", s.toString())
        } catch (e: Exception) {
            Log.i("Failed get location", e.toString())
        }

        return location
            ?: locationDataSource.getLastLocation(
                basePreference.getData(LocationPreferenceKey) ?: LatLong(0.0, 0.0),
            )
    }

    override fun getLastLocation(): LatLong {
        return basePreference.getData(LocationPreferenceKey) ?: LatLong(0.0, 0.0)
    }
}
