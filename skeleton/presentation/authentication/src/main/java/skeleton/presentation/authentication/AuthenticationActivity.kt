package skeleton.presentation.authentication

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import dagger.hilt.android.AndroidEntryPoint
import skeleton.model.authentication.LoginDto
import skeleton.presentation.base.BaseAppsActivity

@AndroidEntryPoint
class AuthenticationActivity : BaseAppsActivity() {
    private val authenticationStateMachine: AuthenticationStateMachine by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LaunchedEffect(
                key1 = Unit,
                block = {
                    authenticationStateMachine.sendEvent(
                        AuthenticationEvent.Login(
                            LoginDto(email = "super_admin@gmail.com", password = "P@ssw0rd"),
                        ),
                    )
                },
            )
        }
    }
}
