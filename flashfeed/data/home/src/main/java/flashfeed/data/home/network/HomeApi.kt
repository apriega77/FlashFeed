package flashfeed.data.home.network

import flashfeed.data.base.network.model.BaseResponse
import flashfeed.data.home.network.model.response.ArticleResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeApi {
    @GET("top-headlines")
    suspend fun getArticles(
        @Query("country") country: String,
    ): BaseResponse<List<ArticleResponse>>
}
