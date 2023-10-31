package flashfeed.presentation.core.webview

import android.annotation.SuppressLint
import android.net.Uri
import android.view.View
import android.webkit.PermissionRequest
import android.webkit.ValueCallback
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.accompanist.web.AccompanistWebChromeClient
import com.google.accompanist.web.LoadingState
import com.google.accompanist.web.WebView
import com.google.accompanist.web.WebViewNavigator
import com.google.accompanist.web.rememberWebViewState
sealed class WidgetWebViewEvent {
    data class GetCurrentUrl(val url: String) : WidgetWebViewEvent()
    data class OnShowFileChooser(val callback: ValueCallback<Array<Uri>>?) : WidgetWebViewEvent()
}

enum class LoadingView {
    PROGRESS_INDICATOR, LOADING_SCREEN
}

data class WidgetWebviewProperties(
    val url: String,
    val loadingView: LoadingView = LoadingView.PROGRESS_INDICATOR,
    val navigator: WebViewNavigator,
)

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun WidgetWebView(
    properties: WidgetWebviewProperties,
    event: (WidgetWebViewEvent) -> Unit,
) {
    val urlState = rememberWebViewState(url = properties.url)

    val eventCallback by rememberUpdatedState(newValue = event)

    LaunchedEffect(key1 = urlState.content) {
        urlState.content.getCurrentUrl()?.let { WidgetWebViewEvent.GetCurrentUrl(it) }
            ?.let { eventCallback(it) }
    }

    val chromeClient = remember {
        object : AccompanistWebChromeClient() {
            override fun onPermissionRequest(request: PermissionRequest?) {
                request?.grant(request.resources)
            }

            override fun onShowFileChooser(
                webView: WebView?,
                filePathCallback: ValueCallback<Array<Uri>>?,
                fileChooserParams: FileChooserParams?,
            ): Boolean {
                eventCallback(WidgetWebViewEvent.OnShowFileChooser(filePathCallback))
                return true
            }
        }
    }

    Column(Modifier.fillMaxSize()) {
        if (properties.loadingView == LoadingView.PROGRESS_INDICATOR) {
            if (urlState.loadingState is LoadingState.Loading) {
                LinearProgressIndicator(
                    progress = (urlState.loadingState as LoadingState.Loading).progress,
                    modifier = Modifier.fillMaxWidth(),
                    color = Color(0xFFFB6A00),
                )
            }
        }

        Box(Modifier.fillMaxSize()) {
            WebView(
                state = urlState,
                modifier = Modifier.fillMaxSize(),
                onCreated = { webView ->
                    webView.setInitialScale(1)
                    webView.setLayerType(View.LAYER_TYPE_HARDWARE, null)
                    webView.settings.apply {
                        javaScriptEnabled = true
                        cacheMode = WebSettings.LOAD_NO_CACHE
                        mediaPlaybackRequiresUserGesture = true
                        domStorageEnabled = true
                        javaScriptCanOpenWindowsAutomatically = true
                        allowContentAccess = true
                        builtInZoomControls = true
                        displayZoomControls = false
                        setSupportZoom(true)
                        useWideViewPort = true
                        loadWithOverviewMode = true
                    }
                },
                navigator = properties.navigator,
                chromeClient = chromeClient,
            )
        }
    }
}
