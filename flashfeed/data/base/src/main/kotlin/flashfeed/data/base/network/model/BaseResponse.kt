package flashfeed.data.base.network.model

open class BaseResponse<T>(
    open var articles: T? = null,
) : BaseEmptyResponse()
