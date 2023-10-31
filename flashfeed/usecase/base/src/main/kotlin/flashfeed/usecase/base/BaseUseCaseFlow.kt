package flashfeed.usecase.base

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.retryWhen
import flashfeed.model.base.UseCaseState

abstract class BaseUseCaseFlow<in Args, Result> : BaseUseCase() {
    protected abstract fun build(args: Args): Flow<Result>
    private val _state = MutableStateFlow<UseCaseState<Flow<Result>>>(UseCaseState.Initial)
    val state: StateFlow<UseCaseState<Flow<Result>>> = _state
    companion object {
        const val maxRetries = 3
    }

    operator fun invoke(args: Args): Flow<Result> {
        _state.value = UseCaseState.Initial
        _state.value = UseCaseState.Executing(0)
        return flow {
            withExecutionTimeout {
                val result = build(args)
                emitAll(result)
                _state.value = UseCaseState.Success(result)
            }
        }.retryWhen { cause, attempt ->
            _state.value = UseCaseState.Executing(attempt.toInt())
            attempt < maxRetries.toLong() && cause is kotlinx.coroutines.TimeoutCancellationException
        }.flowOn(coroutineContext).catch {
            if (it !is kotlinx.coroutines.TimeoutCancellationException) {
                _state.value = UseCaseState.Failed(it)
                throw it
            }
        }
    }
}
