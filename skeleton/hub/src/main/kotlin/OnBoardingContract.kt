import android.app.Activity
import base.BaseAppActivityContract
import base.model.NoArgs
import base.model.NoResult
import kotlin.reflect.KClass

class OnBoardingContract(override val activityClass: KClass<out Activity>) :
    BaseAppActivityContract<NoArgs, NoResult>(NoArgs::class, NoResult::class)
