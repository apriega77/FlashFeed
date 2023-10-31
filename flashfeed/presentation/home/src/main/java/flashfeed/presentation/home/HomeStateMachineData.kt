package flashfeed.presentation.home

import flashfeed.model.home.BottomNavItem

val bottomNavItem = listOf(
    BottomNavItem(
        R.drawable.ic_home_unselected,
        R.drawable.ic_home_unselected,
        HomeRoute.DASHBOARD.alias,
    ),
    BottomNavItem(
        R.drawable.ic_bookmark_unselected,
        R.drawable.ic_bookmark_unselected,
        HomeRoute.BOOKMARK.alias,
    ),
    BottomNavItem(
        R.drawable.ic_explore_unselected,
        R.drawable.ic_explore_unselected,
        HomeRoute.EXPLORE.alias,
    ),
    BottomNavItem(
        R.drawable.ic_setting_unselected,
        R.drawable.ic_setting_unselected,
        HomeRoute.SETTING.alias,
    ),
)

enum class HomeRoute(val alias: String) {
    DASHBOARD("dashboard-screen"), BOOKMARK("bookmark-screen"), EXPLORE("explore-screen"),
    SETTING("setting-screen")
}