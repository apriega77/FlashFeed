package flashfeed.presentation.base.component.toolbar

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.W700
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WidgetToolbarComposable(
    modifier: Modifier = Modifier,
    content: ToolbarContent,
    @DrawableRes backIcon: Int? = null,
    @DrawableRes actionIcon: Int? = null,
    showBackIcon: Boolean = true,
    backPressed: () -> Unit = {},
    bannerPressed: () -> Unit = {},
    actionPressed: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        if (showBackIcon) {
            IconButton(
                onClick = backPressed,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .size(20.dp),
            ) {
                if (backIcon != null) {
                    Icon(
                        painter = painterResource(id = backIcon),
                        contentDescription = null,
                        tint = Color.Unspecified,
                    )
                } else {
                    Icon(imageVector = Icons.Default.ArrowBack, null)
                }
            }
        }
        when (content) {
            ToolbarContent.EmptyContent -> {}
            is ToolbarContent.ImageContent -> {
                IconButton(
                    onClick = bannerPressed,
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically)
                        .width(content.imageWidth ?: 100.dp)
                        .height(content.imageHeight ?: 20.dp),
                ) {
                    Icon(
                        painter = painterResource(id = content.image ?: 0),
                        contentDescription = null,
                        tint = Color.Unspecified,
                    )
                }
            }
            is ToolbarContent.TextContent -> {
                Text(
                    text = content.text,
                    color = if (content.textColor != null) {
                        colorResource(
                            id = content.textColor,
                        )
                    } else {
                        Color.Black
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp)
                        .align(Alignment.CenterVertically)
                        .clickable { bannerPressed() },
                    textAlign = TextAlign.Center,
                    fontWeight = W700,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
        if (actionIcon != null) {
            IconButton(
                onClick = actionPressed,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .size(20.dp),
            ) {
                Icon(
                    painter = painterResource(id = actionIcon),
                    contentDescription = null,
                    tint = Color.Unspecified,
                )
            }
        }
    }
}
