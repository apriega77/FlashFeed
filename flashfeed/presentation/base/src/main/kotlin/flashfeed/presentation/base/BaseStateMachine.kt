package flashfeed.presentation.base

import android.util.Log
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

abstract class BaseStateMachine<State : Any, Event, Effect> : BaseAppsViewModel() {

    private val initial by lazy { getInitialState() }
    abstract fun getInitialState(): State
    val state by lazy { createState<State>(initial) }
    private val event = createSharedFlow<Event>()
    val effect = createSharedFlow<Effect>()

    fun sendEvent(newEvent: Event) {
        event.setValue(newEvent)
    }

    fun resetState() {
        state.setValue(getInitialState())
    }

    init {
        event.onEach {
            Log.d("STATE_MACHINE", "event = $it")
            mapEvent(it, state.value)
        }.launchIn(viewModelScope)
    }

    protected abstract fun mapEvent(event: Event, lastState: State)
}
