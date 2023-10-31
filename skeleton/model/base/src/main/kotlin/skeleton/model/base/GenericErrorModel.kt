package skeleton.model.base

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GenericErrorModel(
    val title: String = "",
    val description: String = "",
    val icon: Image = Image.Init,
    val primaryText: String = "OK",
    val secondaryText: String = "",
    val primaryLink: String = "",
    val secondaryLink: String = "",
    val dismissedLink: String = "",
) : Parcelable
