package flashfeed.presentation.onboarding

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import flashfeed.presentation.base.BaseAppsActivity
import javax.inject.Inject

@AndroidEntryPoint
class OnboardingActivity : BaseAppsActivity() {

    @Inject
    lateinit var router: OnboardingRouter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val systemUi = rememberSystemUiController()
            systemUi.setStatusBarColor(Color.Transparent, true)
            OnboardingScreen {
                router.goToHome()
            }
        }
    }
}