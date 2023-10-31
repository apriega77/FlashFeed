package skeleton.presentation.core

import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import base.presentation.deeplink.DeepLinkRouter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import skeleton.model.base.BaseAppsError
import skeleton.model.base.Callback
import skeleton.model.base.GenericErrorModel
import skeleton.presentation.base.BaseAppsErrorHandler
import skeleton.presentation.base.BaseAppsUseCaseErrorMapper

class BaseAppsErrorHandlerImpl(
    override val activity: FragmentActivity,
    private val errorMapper: BaseAppsUseCaseErrorMapper,
    private val deepLinkRouter: DeepLinkRouter,
) : BaseAppsErrorHandler {

    init {
        with(activity) {
            lifecycleScope.launchWhenStarted {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    errorMapper.errorEvents.onEach {
                        onErrorAction(it)
                    }.launchIn(this)
                }
            }
        }
    }

    private var eventCallback: Callback<Unit, Unit>? = null

    override fun onErrorAction(error: BaseAppsError) {
        when (error) {
            BaseAppsError.GeneralError -> {}
            is BaseAppsError.GenericError -> {}
            BaseAppsError.NetworkError -> {}
            BaseAppsError.SecurityError -> {}
            is BaseAppsError.SessionExpired -> {}
            BaseAppsError.SystemError -> {}
        }
    }

    @Suppress("UnusedParameter", "UnusedPrivateMember")
    private fun showErrorSheet(model: GenericErrorModel) {

    }

    override fun listenEvent(callback: (Unit) -> Unit) {
        eventCallback = callback
    }
}
