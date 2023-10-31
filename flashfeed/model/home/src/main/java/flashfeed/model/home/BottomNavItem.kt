package flashfeed.model.home

import androidx.annotation.DrawableRes

data class BottomNavItem(
    @DrawableRes val iconSelected: Int,
    @DrawableRes val iconUnselected: Int,
    val route: String,
)
