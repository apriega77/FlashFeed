package base.presentation

import androidx.fragment.app.FragmentActivity

interface DefaultErrorHandler<Error> {

    val activity: FragmentActivity

    fun onErrorAction(error: Error)
}
