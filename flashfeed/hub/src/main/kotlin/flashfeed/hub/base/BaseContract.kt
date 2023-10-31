package flashfeed.hub.base

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Parcelable
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import flashfeed.model.base.NoArgs
import flashfeed.model.base.NoResult
import flashfeed.model.base.Route
import kotlin.reflect.KClass

abstract class BaseContract<Args : Parcelable, Result : Parcelable>(
    val argsClass: KClass<Args>,
    val resultClass: KClass<Result>,
) : ActivityResultContract<Args?, Result?>(),
    Route {

    val type: Type by lazy {
        when {
            argsClass == NoArgs::class && resultClass == NoResult::class -> Type.NOARGS_NORESULT
            argsClass != NoArgs::class && resultClass == NoResult::class -> Type.ARGS_NORESULT
            argsClass == NoArgs::class && resultClass != NoResult::class -> Type.NOARGS_RESULT
            else -> Type.ARGS_RESULT
        }
    }

    enum class Type {
        NOARGS_NORESULT,
        ARGS_NORESULT,
        NOARGS_RESULT,
        ARGS_RESULT,
    }

    abstract val contractDestination: ContractDestination

    companion object {
        private const val INTENT_ARGS = "contract.intent.args"
        private const val INTENT_RESULT = "contract.intent.result"
    }

    override fun createIntent(context: Context, input: Args?): Intent {
        return when (contractDestination) {
            is ContractDestination.Activity -> Intent(
                context,
                (contractDestination as ContractDestination.Activity).activityClass.java,
            )
            is ContractDestination.IntentAction -> {
                val contractIntent = contractDestination as ContractDestination.IntentAction
                Intent(
                    contractIntent.intentAction,
                ).apply {
                    if (contractIntent.uri != null) data = contractIntent.uri
                }
            }
        }.apply {
            putExtra(INTENT_ARGS, input)
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Result? {
        if (resultCode != Activity.RESULT_OK) {
            return null
        }
        return intent?.let { intent.getParcelableExtra(INTENT_RESULT) as Result? }
    }

    fun parseStartIntent(intent: Intent): Args {
        return requireNotNull(intent.getParcelableExtra(INTENT_ARGS)) as Args
    }

    fun parseOptionalStartIntent(intent: Intent): Args? {
        return intent.getParcelableExtra(INTENT_ARGS) as Args?
    }

    fun createResultIntent(result: Result): Intent {
        return Intent().apply {
            putExtra(INTENT_RESULT, result)
        }
    }

    lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
}
