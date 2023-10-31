package skeleton.usecase.core

import skeleton.abstraction.core.LocationRepository
import skeleton.model.base.NoArgs
import skeleton.usecase.base.BaseUseCaseSync

class GetLastLocationUseCase(private val locationRepository: LocationRepository) :
    BaseUseCaseSync<NoArgs, Pair<String, String>>() {
    override fun build(args: NoArgs): Pair<String, String> {
        val location = locationRepository.getLastLocation()
        val latitude = location.latitude.takeIf { it.compareTo(0.0) != 0 }?.toString().orEmpty()
        val longitude = location.longitude.takeIf { it.compareTo(0.0) != 0 }?.toString().orEmpty()
        return Pair(latitude, longitude)
    }
}
