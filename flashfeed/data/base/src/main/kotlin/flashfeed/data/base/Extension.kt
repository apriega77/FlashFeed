package flashfeed.data.base

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import flashfeed.data.base.network.model.BaseEmptyResponse
import flashfeed.data.base.network.model.BaseResponse
import retrofit2.HttpException

suspend inline fun <reified T> launchApiSafely(apiCall: suspend () -> BaseResponse<T>): BaseResponse<T> {
    return try {
        apiCall()
    } catch (e: HttpException) {
        val typeToken = object : TypeToken<BaseResponse<T>>() {}.type
        val response = e.response()?.errorBody()?.string()?.let {
            Gson().fromJson<BaseResponse<T>>(it, typeToken)
        } ?: throw Exception()
        response
    }
}

suspend inline fun launchApiSafelyEmpty(apiCall: suspend () -> BaseEmptyResponse): BaseEmptyResponse {
    return try {
        apiCall()
    } catch (e: HttpException) {
        val response: BaseEmptyResponse = e.response()?.errorBody()?.string()?.let {
            Gson().fromJson(it, BaseEmptyResponse::class.java)
        } ?: throw Exception()
        response
    }
}

