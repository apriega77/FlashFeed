package flashfeed.hub

import android.app.Activity
import flashfeed.hub.base.BaseAppActivityContract
import flashfeed.model.base.NoResult
import flashfeed.model.core.WebViewProperties
import kotlin.reflect.KClass

class WebViewContract(override val activityClass: KClass<out Activity>) :
    BaseAppActivityContract<WebViewProperties, NoResult>(WebViewProperties::class, NoResult::class)
