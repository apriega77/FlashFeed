package flashfeed.presentation.onboarding

import androidx.fragment.app.FragmentActivity
import dagger.hilt.android.scopes.ActivityScoped
import flashfeed.hub.HomeContract
import flashfeed.hub.base.BaseRouter
import flashfeed.hub.base.NavigationData
import flashfeed.hub.base.RouterHandler
import flashfeed.model.base.NoResult
import javax.inject.Inject

@ActivityScoped
class OnboardingRouter @Inject constructor(
    override val activity: FragmentActivity,
    override val map: NavigationData,
    override val routerHandler: RouterHandler,
) : BaseRouter() {

    fun goToHome() {
        launch<HomeContract>() {
            activity.finishAffinity()
        }
    }
}
