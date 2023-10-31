package flashfeed.usecase.base

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withTimeout


abstract class BaseUseCase {

    companion object {
        const val MILLISECONDS = 1000L
        const val timeout = 20
    }

    open val timeOutInSeconds = timeout
    private fun getExecutionTimeOut() = timeOutInSeconds * MILLISECONDS

    internal suspend fun <T> withExecutionTimeout(func: suspend () -> T): T {
        return withTimeout(getExecutionTimeOut()) {
            func.invoke()
        }
    }

    var coroutineContext = Dispatchers.IO
}
