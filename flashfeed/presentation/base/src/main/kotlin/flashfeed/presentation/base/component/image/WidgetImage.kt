package flashfeed.presentation.base.component.image

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import flashfeed.model.base.Image

@Composable
fun WidgetImage(
    image: Image,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
) {
    val imageHolder = remember(image) {
        ImageHolder.from(image)
    }

    WidgetImage(
        imageHolder,
        modifier,
        contentDescription,
        alignment,
        contentScale,
        alpha,
        colorFilter,
    )
}

@Immutable
sealed class ImageHolder {
    @Immutable
    data class Url(val url: String) : ImageHolder()

    @Immutable
    data class Res(val res: Int) : ImageHolder()

    @Immutable
    data class Bitmap(val bitmap: android.graphics.Bitmap) : ImageHolder()

    @Immutable
    data class Icon(val icon: android.graphics.drawable.Icon) : ImageHolder()

    companion object {
        val Init = Res(0)
        fun from(image: Image) = when (image) {
            is Image.Bitmap -> Bitmap(image.bitmap)
            is Image.Icon -> Icon(image.icon)
            is Image.Res -> Res(image.res)
            is Image.Url -> Url(image.url)
        }
    }
}

@Composable
fun WidgetImage(
    image: ImageHolder,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
) {
    when (image) {
        is ImageHolder.Bitmap -> {
            Image(
                bitmap = (image as ImageHolder.Bitmap).bitmap.asImageBitmap(),
                contentDescription = contentDescription,
                modifier = modifier,
                alignment = alignment,
                contentScale = contentScale,
                alpha = alpha,
                colorFilter = colorFilter,
            )
        }
        is ImageHolder.Res -> {
            Image(
                painter = painterResource(id = (image as ImageHolder.Res).res),
                contentDescription = contentDescription,
                modifier = modifier,
                alignment = alignment,
                contentScale = contentScale,
                alpha = alpha,
                colorFilter = colorFilter,
            )
        }
        is ImageHolder.Url -> {
            AsyncImage(
                model = (image as ImageHolder.Url).url,
                contentDescription = contentDescription,
                modifier = modifier,
                alignment = alignment,
                contentScale = contentScale,
                alpha = alpha,
                colorFilter = colorFilter,
            )
        }
        is ImageHolder.Icon -> {}
    }
}
