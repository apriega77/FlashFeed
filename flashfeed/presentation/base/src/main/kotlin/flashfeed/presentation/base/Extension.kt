package flashfeed.presentation.base

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import flashfeed.model.base.BaseAppsError

fun BaseViewModel.createLauncher(baseAppsUseCaseErrorMapper: BaseAppsUseCaseErrorMapper) =
    object : UseCaseLauncher<BaseAppsError, BaseAppsUseCaseErrorMapper> {

        override val scope: CoroutineScope
            get() = viewModelScope

        override fun getErrorMapper(): BaseAppsUseCaseErrorMapper {
            return baseAppsUseCaseErrorMapper
        }
    }
