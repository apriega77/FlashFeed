package flashfeed.model.core

import androidx.annotation.StringRes

data class Language(@StringRes val id: Int, val extraArgs: List<Any> = listOf())
