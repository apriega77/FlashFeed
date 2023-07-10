package skeleton.data.base.network.model

import com.google.gson.annotations.SerializedName

open class BaseEmptyResponse(
    open var code: String? = null,
    open var message: String? = null,
    @SerializedName("error")
    open var errorMessage: String? = null,
    @SerializedName("error_data")
    open var error: ErrorResponse? = null,
)
