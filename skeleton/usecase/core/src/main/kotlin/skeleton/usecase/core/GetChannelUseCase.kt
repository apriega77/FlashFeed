package skeleton.usecase.core

import base.model.NoArgs
import base.usecase.BaseUseCaseSync
import skeleton.abstraction.core.CoreRepository
import skeleton.model.core.Channel

class GetChannelUseCase(private val coreRepository: CoreRepository) :
    BaseUseCaseSync<NoArgs, Channel>() {
    override fun build(args: NoArgs): Channel {
        return coreRepository.getChannel()
    }
}
