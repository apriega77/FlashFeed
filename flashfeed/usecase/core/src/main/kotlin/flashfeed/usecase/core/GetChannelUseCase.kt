package flashfeed.usecase.core

import flashfeed.abstraction.core.CoreRepository
import flashfeed.model.base.NoArgs
import flashfeed.model.core.Channel
import flashfeed.usecase.base.BaseUseCaseSync

class GetChannelUseCase(private val coreRepository: CoreRepository) :
    BaseUseCaseSync<NoArgs, Channel>() {
    override fun build(args: NoArgs): Channel {
        return coreRepository.getChannel()
    }
}
