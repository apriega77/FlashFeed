package flashfeed.presentation.core.webview

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.accompanist.web.rememberWebViewNavigator
import dagger.hilt.android.AndroidEntryPoint
import flashfeed.model.core.WebViewProperties
import flashfeed.presentation.base.BaseAppsActivity
import flashfeed.presentation.base.component.toolbar.ToolbarContent
import flashfeed.presentation.base.component.toolbar.WidgetToolbarComposable
import flashfeed.presentation.core.R

@AndroidEntryPoint
class WebViewActivity : BaseAppsActivity() {

    private val data by parcelableArgs<WebViewProperties>()

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter", "SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val url by remember { mutableStateOf(data?.url.orEmpty()) }
            val navigator = rememberWebViewNavigator()
            val systemUi = rememberSystemUiController()
            systemUi.setStatusBarColor(Color.White, true)
            Column {
                WidgetToolbarComposable(
                    content = ToolbarContent.TextContent(
                        text = data?.title.orEmpty(),
                    ),
                    backIcon = R.drawable.ic_webview_close,
                    backPressed = {
                        finish()
                    },
                    actionIcon = R.drawable.ic_webview_refresh,
                    actionPressed = {
                        navigator.reload()
                    },
                )

                WidgetWebView(
                    properties = WidgetWebviewProperties(
                        url = url,
                        navigator = navigator,
                    ),
                ) {
                }
            }
        }
    }
}
