package flashfeed.usecase.base

import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import flashfeed.model.base.UseCaseState

abstract class BaseUseCaseAsync<in Args, Result> : BaseUseCase() {
    protected abstract suspend fun build(args: Args): Result
    private val _state = MutableStateFlow<UseCaseState<Result>>(UseCaseState.Initial)
    val state: StateFlow<UseCaseState<Result>> = _state

    companion object {
        const val maxRetries = 3
    }

    fun resetState() {
        _state.value = UseCaseState.Initial
    }

    suspend operator fun invoke(args: Args): Result {
        resetState()
        repeat(maxRetries - 1) {
            _state.value = UseCaseState.Executing(it)
            try {
                return invokeOperation(args).also {
                    _state.value = UseCaseState.Success(it)
                }
            } catch (e: Exception) {
                if (e !is TimeoutCancellationException) {
                    _state.value = UseCaseState.Failed(e)
                    throw e
                }
            }
        }
        _state.value = UseCaseState.Executing(maxRetries - 1)
        return try {
            invokeOperation(args).also {
                _state.value = UseCaseState.Success(it)
            }
        } catch (e: Exception) {
            _state.value = UseCaseState.Failed(e)
            throw e
        }
    }

    private suspend fun invokeOperation(args: Args): Result {
        return withContext(coroutineContext) {
            withExecutionTimeout {
                build(args)
            }
        }
    }
}
