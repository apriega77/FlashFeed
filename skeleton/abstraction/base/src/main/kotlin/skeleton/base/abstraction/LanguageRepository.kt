package skeleton.base.abstraction

import androidx.annotation.StringRes

interface LanguageRepository {
    fun getString(@StringRes id: Int, vararg extraArgs: Any): String
    fun getString(key: String, vararg extraArgs: Any): String
}
