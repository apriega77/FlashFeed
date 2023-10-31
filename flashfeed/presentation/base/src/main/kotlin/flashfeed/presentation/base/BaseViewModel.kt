package flashfeed.presentation.base

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    protected fun <T> createState(initialValue: T): StateFlow<T> = MutableStateFlow(initialValue)
    fun <T> StateFlow<T>.setValue(state: T) {
        if (this is MutableStateFlow) {
            Log.d("STATE_MACHINE", "state = $state")
            this.value = state
        }
    }

    protected fun <T> StateFlow<T>.updateState(function: (T) -> T) {
        if (this is MutableStateFlow) {
            this.update(function)
        }
    }

    protected fun <T> createSharedFlow(): MutableSharedFlow<T> = MutableSharedFlow<T>().apply {
        shareIn(viewModelScope, SharingStarted.WhileSubscribed())
    }

    fun <T> SharedFlow<T>.setValue(data: T) {
        if (this is MutableSharedFlow) {
            viewModelScope.launch {
                Log.d("STATE_MACHINE", "effect = $data")
                emit(data)
            }
        }
    }
}
