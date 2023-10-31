package skeleton.usecase.core

import skeleton.abstraction.core.CoreRepository
import skeleton.model.base.NoArgs
import skeleton.model.core.Channel
import skeleton.usecase.base.BaseUseCaseSync

class GetChannelUseCase(private val coreRepository: CoreRepository) :
    BaseUseCaseSync<NoArgs, Channel>() {
    override fun build(args: NoArgs): Channel {
        return coreRepository.getChannel()
    }
}
