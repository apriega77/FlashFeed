package flashfeed.presentation.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import flashfeed.model.base.Callback
import flashfeed.model.base.Image
import flashfeed.presentation.base.component.image.WidgetImage

@Composable
fun OnboardingScreen(onButtonClicked: () -> Unit) {
    ConstraintLayout(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFFFFFFFF))) {

        val (imgRef, titleRef, descRef, buttonRef) = createRefs()

        WidgetImage(
            image = Image.Res(R.drawable.img_city),
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .constrainAs(imgRef) {
                    top.linkTo(parent.top, 8.dp)
                    start.linkTo(parent.start, 8.dp)
                    end.linkTo(parent.end, 8.dp)
                    bottom.linkTo(titleRef.top, 20.dp)
                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                },
            contentScale = ContentScale.Crop,
        )

        Text(
            text = "News from around the world for you",
            modifier = Modifier.constrainAs(titleRef) {
                bottom.linkTo(descRef.top, 16.dp)
                start.linkTo(parent.start, 16.dp)
                end.linkTo(parent.end, 16.dp)
                width = Dimension.fillToConstraints
            },
            style = TextStyle(
                fontSize = 28.sp,
                lineHeight = 28.sp,
                fontFamily = FontFamily(Font(flashfeed.presentation.base.R.font.inter_regular)),
                fontWeight = FontWeight(700),
                color = Color(0xFF000000),
                textAlign = TextAlign.Center,
            ),
        )

        Text(
            text = "Best time to read, take your time to read a little more of this world",
            modifier = Modifier.constrainAs(descRef) {
                bottom.linkTo(buttonRef.top, 16.dp)
                start.linkTo(parent.start, 16.dp)
                end.linkTo(parent.end, 16.dp)
            },
            style = TextStyle(
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(flashfeed.presentation.base.R.font.inter_regular)),
                fontWeight = FontWeight(400),
                color = Color(0xFF595862),
                textAlign = TextAlign.Center,
            ),
        )

        Button(
            onClick = { onButtonClicked.invoke() },
            modifier = Modifier.constrainAs(buttonRef) {
                bottom.linkTo(parent.bottom, 20.dp)
                end.linkTo(parent.end, 32.dp)
                start.linkTo(parent.start, 32.dp)
                width = Dimension.fillToConstraints
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFB6A00)),
            shape = RoundedCornerShape(50.dp),
            contentPadding = PaddingValues(vertical = 14.dp, horizontal = 14.dp),
        ) {
            Text(
                text = "Get Started",
                color = Color(0xFFFFFFFF),
                style = TextStyle(
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(flashfeed.presentation.base.R.font.inter_regular)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFFFFFF),
                    textAlign = TextAlign.Center,
                ),
            )
        }


    }
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
internal fun PreviewOnboardingScreen() {
    OnboardingScreen {

    }
}