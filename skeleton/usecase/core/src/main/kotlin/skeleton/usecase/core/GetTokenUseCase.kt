package skeleton.usecase.core

import base.model.NoArgs
import base.usecase.BaseUseCaseSync
import skeleton.abstraction.core.TokenRepository

class GetTokenUseCase(private val tokenRepository: TokenRepository) :
    BaseUseCaseSync<NoArgs, String>() {
    override fun build(args: NoArgs): String {
        return tokenRepository.getSessionToken()
    }
}
