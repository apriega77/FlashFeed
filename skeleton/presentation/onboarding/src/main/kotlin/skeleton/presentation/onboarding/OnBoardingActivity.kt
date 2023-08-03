package skeleton.presentation.onboarding

import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import dagger.hilt.android.AndroidEntryPoint
import skeleton.presentation.base.BaseAppsActivity

@AndroidEntryPoint
class OnBoardingActivity : BaseAppsActivity() {
    private val onBoardingStateMachine: OnBoardingStateMachine by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            onBoardingStateMachine.sendEvent(OnBoardingEvent.GetWelcomePages)
            val state by onBoardingStateMachine.state.collectAsState()
            Toast.makeText(
                this@OnBoardingActivity,
                state.welcomePages.size.toString(),
                Toast.LENGTH_SHORT,
            )
                .show()
        }
    }
}
