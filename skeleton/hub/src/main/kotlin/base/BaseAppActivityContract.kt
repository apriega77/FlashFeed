package base

import android.app.Activity
import android.os.Parcelable
import base.hub.BaseContract
import base.hub.ContractDestination
import kotlin.reflect.KClass

abstract class BaseAppActivityContract<Args : Parcelable, Result : Parcelable>(
    argsClass: KClass<Args>,
    resultClass: KClass<Result>,
) : BaseContract<Args, Result>(argsClass, resultClass) {

    abstract val activityClass: KClass<out Activity>

    override val contractDestination: ContractDestination
        get() = ContractDestination.Activity(activityClass)
}
