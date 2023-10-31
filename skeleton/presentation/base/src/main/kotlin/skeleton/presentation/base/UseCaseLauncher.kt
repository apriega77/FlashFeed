package skeleton.presentation.base

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import skeleton.model.base.NoArgs
import skeleton.model.base.NoResult
import skeleton.usecase.base.BaseUseCaseAsync
import skeleton.usecase.base.BaseUseCaseFlow
import skeleton.usecase.base.BaseUseCaseSync

interface UseCaseLauncher<
    Error,
    Mapper : UseCaseErrorMapper<Error>,
    > {

    fun getErrorMapper(): Mapper

    val scope: CoroutineScope

    fun <T, Args, Result> launch(
        useCase: T,
        args: Args,
        resultCallback: (Result) -> Unit,
    )
    where T : BaseUseCaseFlow<Args, Result> {
        useCase.invoke(args).onEach {
            resultCallback(it)
        }.catch {
            getErrorMapper().sendException(scope, it)
        }.launchIn(scope)
    }

    fun <T, Result> launch(
        useCase: T,
        resultCallback: (Result) -> Unit,
    )
    where T : BaseUseCaseFlow<NoArgs, Result> {
        useCase.invoke(NoArgs()).onEach {
            resultCallback(it)
        }.catch {
            getErrorMapper().sendException(scope, it)
        }.launchIn(scope)
    }

    fun <T, Args, Result> launch(
        useCase: T,
        args: Args,
        resultCallback: (Result) -> Unit,
    )
    where T : BaseUseCaseAsync<Args, Result> {
        scope.launch {
            try {
                val result = useCase(args)
                resultCallback(result)
            } catch (e: Exception) {
                getErrorMapper().sendException(scope, e)
            }
        }
    }

    fun <T, Result> launch(
        useCase: T,
        resultCallback: (Result) -> Unit,
    )
    where T : BaseUseCaseAsync<NoArgs, Result> {
        scope.launch {
            try {
                val result = useCase(NoArgs())
                resultCallback(result)
            } catch (e: Exception) {
                getErrorMapper().sendException(scope, e)
            }
        }
    }

    fun <T> launch(
        useCase: T,
        resultCallback: () -> Unit,
    )
    where T : BaseUseCaseAsync<NoArgs, NoResult> {
        scope.launch {
            try {
                useCase(NoArgs())
                resultCallback()
            } catch (e: Exception) {
                getErrorMapper().sendException(scope, e)
            }
        }
    }

    fun <T, Args> launch(
        useCase: T,
        args: Args,
        resultCallback: () -> Unit,
    )
    where T : BaseUseCaseAsync<Args, NoResult> {
        scope.launch {
            try {
                val result = useCase(args)
                resultCallback()
            } catch (e: Exception) {
                getErrorMapper().sendException(scope, e)
            }
        }
    }

    fun <T, Result> launch(
        useCase: T,
        resultCallback: (Result) -> Unit,
    )
    where T : BaseUseCaseSync<NoArgs, Result> {
        try {
            resultCallback(useCase(NoArgs()))
        } catch (e: Exception) {
            getErrorMapper().sendException(scope, e)
        }
    }
}
