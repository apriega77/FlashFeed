package flashfeed.usecase.home

import flashfeed.abstraction.home.HomeRepository
import flashfeed.model.home.Article
import flashfeed.model.home.ArticleRequest
import flashfeed.usecase.base.BaseUseCaseAsync

class GetArticlesUseCase(private val homeRepository: HomeRepository) :
    BaseUseCaseAsync<ArticleRequest, List<Article>>() {
    override suspend fun build(args: ArticleRequest): List<Article> {
        return homeRepository.getArticles(args)
    }
}
