package flashfeed.model.core

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Token(
    val accessToken: String,
    val refreshToken: String,
    val biometricKey: String,
) : Parcelable {
    companion object {
        val Init = Token("", "", "")
    }
}
