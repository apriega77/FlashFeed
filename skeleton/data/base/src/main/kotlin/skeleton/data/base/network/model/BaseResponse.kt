package skeleton.data.base.network.model

open class BaseResponse<T>(
    open var data: T? = null,
) : BaseEmptyResponse()
