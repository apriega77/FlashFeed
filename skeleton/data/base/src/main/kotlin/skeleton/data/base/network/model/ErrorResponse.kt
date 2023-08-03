package skeleton.data.base.network.model

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    val title: String? = null,
    val description: String? = null,
    val icon: String? = null,
    @SerializedName("primary_text")
    val primaryText: String? = null,
    @SerializedName("secondary_text")
    val secondaryText: String? = null,
    @SerializedName("primary_link")
    val primaryLink: String? = null,
    @SerializedName("secondary_link")
    val secondaryLink: String? = null,
    @SerializedName("dismissed_link")
    val dismissedLink: String? = null,
)
