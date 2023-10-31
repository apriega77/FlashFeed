package flashfeed.hub

import android.app.Activity
import flashfeed.hub.base.BaseAppActivityContract
import flashfeed.model.base.NoArgs
import flashfeed.model.base.NoResult
import kotlin.reflect.KClass

class HomeContract(
    override val activityClass: KClass<out Activity>,
) : BaseAppActivityContract<NoArgs, NoResult>(
    NoArgs::class,
    NoResult::class,
)
