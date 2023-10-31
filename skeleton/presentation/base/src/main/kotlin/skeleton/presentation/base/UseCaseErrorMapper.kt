package skeleton.presentation.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharedFlow

interface UseCaseErrorMapper<T> {
    val errorEvents: SharedFlow<T>
    fun sendException(scope: CoroutineScope, t: Throwable)
}
