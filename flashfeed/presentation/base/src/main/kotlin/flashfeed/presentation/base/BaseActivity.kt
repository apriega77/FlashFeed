package flashfeed.presentation.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import androidx.annotation.CallSuper
import androidx.fragment.app.FragmentActivity
import java.util.*
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

abstract class BaseActivity : FragmentActivity() {

    override fun attachBaseContext(newBase: Context?) {
        newBase?.run {
            val config = resources.configuration
            config.setLocale(Locale("ID"))
            super.attachBaseContext(createConfigurationContext(config))
        }
    }

    inline fun <reified Args : Parcelable> Activity.parcelableArgs():
        ReadOnlyProperty<Activity, Args?> {
        return object : ReadOnlyProperty<Any, Args?> {
            private var value: Args? = null
            private var initiated: Boolean = false

            override fun getValue(thisRef: Any, property: KProperty<*>): Args? {
                if (!initiated) {
                    value = try {
                        val value = intent.getParcelableExtra<Args>(
                            "contract.intent.args",
                        )
                        initiated = true
                        value
                    } catch (e: Exception) {
                        null
                    }
                }
                return value
            }
        }
    }

    protected open fun startTransition() = transition(R.anim.enter_from_right, R.anim.exit_to_left)
    protected open fun finishTransition() = transition(R.anim.enter_from_left, R.anim.exit_to_right)
    protected open fun transition(enterAnim: Int = 0, exitAnim: Int = 0) =
        overridePendingTransition(enterAnim, exitAnim)

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startTransition()
    }

    override fun onPause() {
        super.onPause()
        if (isTaskRoot) {
            transition(R.anim.slide_in_up, R.anim.slide_out_down)
        } else {
            finishTransition()
        }
    }
}
