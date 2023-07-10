package skeleton.presentation.authentication

import android.util.Log
import base.model.NoArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import skeleton.presentation.base.BaseAppsUseCaseErrorMapper
import skeleton.presentation.base.BaseStateMachine
import skeleton.usecase.authentication.LoginUseCase
import javax.inject.Inject

@HiltViewModel
class AuthenticationStateMachine @Inject constructor(
    override var errorMapper: BaseAppsUseCaseErrorMapper,
    private val loginUseCase: LoginUseCase,
) : BaseStateMachine<AuthenticationState, AuthenticationEvent, NoArgs>() {
    override fun getInitialState(): AuthenticationState {
        return AuthenticationState.Initial
    }

    override fun mapEvent(event: AuthenticationEvent, lastState: AuthenticationState) {
        when (event) {
            is AuthenticationEvent.Login -> {
                launcher.launch(
                    loginUseCase,
                    event.loginDto,
                ) {
                    Log.d("STATE_STATUS", "SUCCESS")
                    state.setValue(AuthenticationState.Loaded)
                }
            }
        }
    }
}
