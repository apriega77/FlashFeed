package flashfeed.model.base

sealed class UseCaseState<out T> {
    object Initial : UseCaseState<Nothing>()
    data class Executing(val attempt: Int) : UseCaseState<Nothing>()
    data class Success<out T>(val data: T) : UseCaseState<T>()
    data class Failed(val cause: Throwable) : UseCaseState<Nothing>()
}
