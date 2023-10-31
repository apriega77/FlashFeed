package flashfeed.data.home.datasource

import flashfeed.abstraction.home.HomeRemoteDataSource
import flashfeed.data.base.launchApiSafely
import flashfeed.data.home.network.HomeApi
import flashfeed.model.base.Image
import flashfeed.model.home.Article
import flashfeed.model.home.ArticleRequest
import flashfeed.model.home.Source
import javax.inject.Inject

class HomeRemoteDataSourceImpl @Inject constructor(private val homeApi: HomeApi) :
    HomeRemoteDataSource {
    override suspend fun getArticles(args: ArticleRequest): List<Article> {
        val response = launchApiSafely {
            homeApi.getArticles(country = args.country)
        }
        val data = response.articles?.mapNotNull {
            Article(
                author = it.author.orEmpty(),
                content = it.content.orEmpty(),
                description = it.description.orEmpty(),
                publishedAt = it.publishedAt.toString(),
                source = Source(
                    id = it.sourceResponse?.id.orEmpty(),
                    name = it.sourceResponse?.name.orEmpty(),
                ),
                title = it.title.orEmpty(),
                url = it.url.orEmpty(),
                urlToImage = Image.Url(it.urlToImage.orEmpty()),
            )
        } ?: listOf()

        return data
    }
}
