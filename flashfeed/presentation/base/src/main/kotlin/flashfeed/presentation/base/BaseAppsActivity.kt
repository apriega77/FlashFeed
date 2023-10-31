package flashfeed.presentation.base

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.annotation.CallSuper
import javax.inject.Inject

abstract class BaseAppsActivity : BaseActivity() {

    @Inject
    lateinit var errorHandler: BaseAppsErrorHandler

    @Inject
    lateinit var errorMapper: BaseAppsUseCaseErrorMapper

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        @SuppressLint("SourceLockedOrientationActivity")
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        errorHandler.listenEvent(::errorHandlerCallback)
    }

    protected open fun errorHandlerCallback(events: Unit) {
    }

    @CallSuper
    override fun onUserInteraction() {
        super.onUserInteraction()
    }
}
