package skeleton.abstraction.core

import skeleton.model.core.LatLong

interface LocationRepository {
    suspend fun fetchCurrentLocation(): LatLong
    fun getLastLocation(): LatLong
}
