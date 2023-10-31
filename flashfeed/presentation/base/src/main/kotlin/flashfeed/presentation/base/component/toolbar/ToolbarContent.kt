package flashfeed.presentation.base.component.toolbar

import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp

@Immutable
sealed class ToolbarContent {
    @Immutable
    data class TextContent(
        val text: String,
        @ColorRes
        val textColor: Int? = null,
    ) : ToolbarContent()

    @Immutable
    data class ImageContent(
        @DrawableRes val image: Int,
        val imageHeight: Dp? = null,
        val imageWidth: Dp? = null,
    ) : ToolbarContent()

    object EmptyContent : ToolbarContent()
}
