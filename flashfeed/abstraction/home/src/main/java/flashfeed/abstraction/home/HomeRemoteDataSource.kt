package flashfeed.abstraction.home

import flashfeed.model.home.Article
import flashfeed.model.home.ArticleRequest

interface HomeRemoteDataSource {
    suspend fun getArticles(args: ArticleRequest): List<Article>
}
