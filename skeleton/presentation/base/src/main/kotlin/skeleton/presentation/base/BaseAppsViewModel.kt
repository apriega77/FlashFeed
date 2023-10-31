package skeleton.presentation.base

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn

abstract class BaseAppsViewModel : BaseViewModel() {

    abstract var errorMapper: BaseAppsUseCaseErrorMapper

    val errorEvents by lazy {
        errorMapper.errorEvents.apply {
            shareIn(viewModelScope, SharingStarted.WhileSubscribed())
        }
    }

    val launcher by lazy { createLauncher(errorMapper) }
}
