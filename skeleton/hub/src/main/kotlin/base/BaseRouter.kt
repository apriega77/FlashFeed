package base

import android.content.Intent
import android.os.Build
import android.os.Parcelable
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.DefaultLifecycleObserver
import skeleton.model.base.NoArgs
import skeleton.model.base.Route

abstract class BaseRouter : DefaultLifecycleObserver {
    protected abstract val activity: FragmentActivity
    protected abstract val map: NavigationData
    protected abstract val routerHandler: RouterHandler
    val registry by lazy { activity.activityResultRegistry }
    val resultLauncherMap = mutableMapOf<Class<*>, ActivityResultLauncher<Intent>>()

    protected inline fun <reified T> launch(
        noinline onSuccess: () -> Unit = {},
    ) where T : Route, T : BaseContract<NoArgs, *> {
        val kClass = T::class
        try {
            val contract = map[kClass] as? BaseContract<*, *> ?: throw Exception()
            val intent = contract.createIntent(activity, null)
            activity.startActivity(intent)
            routerHandler.onNavigated(contract)
            onSuccess()
        } catch (e: Exception) {
            routerHandler.onRouteNotFound(kClass.simpleName.toString())
        }
    }

    protected inline fun <reified T, reified Args : Parcelable> launch(
        args: Args,
        noinline onSuccess: () -> Unit = {},
    ) where T : Route, T : BaseContract<Args, *> {
        val kClass = T::class
        val contract = map[kClass] as? T ?: throw Exception()
        try {
            val intent = contract.createIntent(activity, args)
            activity.startActivity(intent)
            routerHandler.onNavigated(contract)
            onSuccess()
        } catch (e: Exception) {
            routerHandler.onRouteNotFound(kClass.simpleName.toString())
        }
    }

    protected inline fun <reified T, reified Result : Parcelable> launch(
        crossinline onSuccess: (Result) -> Unit = {},
        noinline onFailure: () -> Unit = {},
    ) where T : Route, T : BaseContract<NoArgs, Result> {
        val kclass = T::class
        try {
            val contract = map[kclass] as? BaseContract<*, *>
                ?: throw Exception("Contract not found for $kclass")
            val intent = contract.createIntent(activity, null)
            val activityResultLauncher = getOrCreateActivityResultLauncher<Result> {
                if (it != null) {
                    onSuccess(it)
                } else {
                    onFailure()
                }
            }
            activityResultLauncher.launch(intent)
            routerHandler.onNavigated(contract)
        } catch (e: Exception) {
            routerHandler.onRouteNotFound(kclass.simpleName.toString())
        }
    }

    protected inline fun <reified T, reified Args : Parcelable, reified Result : Parcelable> launch(
        args: Args,
        crossinline onSuccess: (Result) -> Unit = {},
        noinline onFailure: () -> Unit = {},
    ) where T : Route, T : BaseContract<Args, Result> {
        val kclass = T::class
        try {
            val contract = map[kclass] as? BaseContract<*, *>
                ?: throw Exception("Contract not found for $kclass")
            val intent = (map[kclass] as? T)?.createIntent(activity, args)
            val activityResultLauncher = getOrCreateActivityResultLauncher<Result> {
                if (it != null) {
                    onSuccess(it)
                } else {
                    onFailure()
                }
            }
            activityResultLauncher.launch(intent)
            routerHandler.onNavigated(contract)
        } catch (e: Exception) {
            routerHandler.onRouteNotFound(kclass.simpleName.toString())
        }
    }

    protected inline fun <reified Result : Parcelable> getOrCreateActivityResultLauncher(
        crossinline callback: (Result?) -> Unit,
    ): ActivityResultLauncher<Intent> {
        val resultClass = Result::class.java
        val newLauncher = registry.register(
            "contract.intent.result",
            ActivityResultContracts.StartActivityForResult(),
        ) { result ->
            val data = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                result.data?.getParcelableExtra(
                    "contract.intent.result",
                    Result::class.java,
                )
            } else {
                result.data?.getParcelableExtra(
                    "contract.intent.result",
                ) as? Result
            }
            callback(data)
        }
        resultLauncherMap[resultClass] = newLauncher
        return newLauncher
    }

    protected inline fun <reified Result : Parcelable> handleActivityResultCancellation(
        crossinline onCancellation: () -> Unit = {},
    ) {
        val activityResultLauncher = getOrCreateActivityResultLauncher<Result> {
            onCancellation.invoke()
        }
        activityResultLauncher.launch(Intent())
    }
}
