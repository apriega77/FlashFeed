package flashfeed.model.core

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WebViewProperties(val url: String, val title: String) :
    Parcelable
