package flashfeed.presentation.home.dashboard.article

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import flashfeed.model.home.Article
import flashfeed.presentation.base.R
import flashfeed.presentation.base.component.image.WidgetImage

@Composable
fun ArticleItemView(article: Article, onArticleClicked: (Article) -> Unit) {
    ConstraintLayout(
        modifier = Modifier
            .clickable(
                indication = null,
                interactionSource = remember {
                    MutableInteractionSource()
                },
            ) { onArticleClicked(article) }
            .fillMaxWidth()
            .padding(horizontal = 8.dp)
            .padding(bottom = 16.dp),
    ) {
        val (imageRef, authorRef, titleRef) = createRefs()

        WidgetImage(
            image = article.urlToImage,
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .size(120.dp)
                .constrainAs(imageRef) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                },
            contentScale = ContentScale.Crop,
        )

        Text(
            text = article.author,
            modifier = Modifier.constrainAs(authorRef) {
                top.linkTo(parent.top)
                start.linkTo(imageRef.end, 16.dp)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            },
            style = TextStyle(
                fontSize = 16.sp,
                lineHeight = 28.sp,
                fontFamily = FontFamily(Font(R.font.inter_regular)),
                fontWeight = FontWeight(400),
                color = Color(0xFF595862),
                textAlign = TextAlign.Start,
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )

        Text(
            text = article.title,
            modifier = Modifier.constrainAs(titleRef) {
                bottom.linkTo(parent.bottom)
                start.linkTo(imageRef.end, 16.dp)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            },
            style = TextStyle(
                fontSize = 16.sp,
                lineHeight = 28.sp,
                fontFamily = FontFamily(Font(R.font.inter_regular)),
                fontWeight = FontWeight(700),
                color = Color(0xFF000000),
                textAlign = TextAlign.Start,
            ),
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
        )
    }
}