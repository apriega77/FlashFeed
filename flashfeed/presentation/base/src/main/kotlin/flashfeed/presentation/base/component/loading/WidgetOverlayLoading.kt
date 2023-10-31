package flashfeed.presentation.base.component.loading

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun WidgetOverlayLoading(modifier: Modifier = Modifier.fillMaxSize(), isVisible: Boolean) {
    AnimatedVisibility(visible = isVisible, enter = fadeIn(), exit = fadeOut()) {
        if (isVisible) {
            Column(
                modifier = modifier
                    .background(color = Color.White),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(
                    modifier = Modifier
                        .background(color = Color.White, shape = CircleShape)
                        .size(48.dp)
                        .padding(10.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator(
                        color = Color(0xFFFB6A00),
                        strokeWidth = 3.dp,
                    )
                }
            }
        }
    }
}
