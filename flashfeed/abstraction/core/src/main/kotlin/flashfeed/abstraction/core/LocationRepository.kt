package flashfeed.abstraction.core

import flashfeed.model.core.LatLong

interface LocationRepository {
    suspend fun fetchCurrentLocation(): LatLong
    fun getLastLocation(): LatLong
}
