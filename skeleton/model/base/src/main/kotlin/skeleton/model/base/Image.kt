package skeleton.model.base

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class Image : Parcelable {
    @Parcelize
    data class Url(val url: String) : Image()

    @Parcelize
    data class Res(val res: Int) : Image()

    @Parcelize
    data class Bitmap(val bitmap: android.graphics.Bitmap) : Image()

    @Parcelize
    data class Icon(val icon: android.graphics.drawable.Icon) : Image()

    companion object {
        val Init = Res(0)
    }
}
