package flashfeed.data.home.repository

import flashfeed.abstraction.home.HomeRemoteDataSource
import flashfeed.abstraction.home.HomeRepository
import flashfeed.model.home.Article
import flashfeed.model.home.ArticleRequest
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(private val remoteDataSource: HomeRemoteDataSource) :
    HomeRepository {
    override suspend fun getArticles(args: ArticleRequest): List<Article> {
        return remoteDataSource.getArticles(args)
    }
}
