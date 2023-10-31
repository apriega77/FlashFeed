package flashfeed.presentation.home

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import flashfeed.presentation.base.BaseAppsActivity
import flashfeed.presentation.home.dashboard.DashboardMainScreen
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : BaseAppsActivity() {

    @Inject
    lateinit var router: HomeRouter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val systemUi = rememberSystemUiController()
            systemUi.setStatusBarColor(Color.White, true)
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            Scaffold(
                modifier = Modifier.navigationBarsPadding(),
                bottomBar = {
                    BottomAppBar(elevation = 20.dp, backgroundColor = Color.White) {
                        bottomNavItem.forEach { item ->
                            BottomNavigationItem(
                                selected = currentRoute == item.route,
                                selectedContentColor = Color(0xFFFB6A00),
                                unselectedContentColor = Color(0xFFb4abad),
                                onClick = {
                                    when (item.route) {
                                        HomeRoute.DASHBOARD.alias -> {
                                            navController.navigate(item.route) {
                                                navController.graph.startDestinationRoute?.let { startDestination ->
                                                    popUpTo(startDestination) {
                                                        saveState = true
                                                    }
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        }

                                        else -> {

                                        }
                                    }

                                },
                                icon = {
                                    Icon(
                                        if (currentRoute == item.route) {
                                            painterResource(id = item.iconSelected)
                                        } else {
                                            painterResource(
                                                id = item.iconUnselected,
                                            )
                                        },
                                        modifier = Modifier.size(24.dp),
                                        contentDescription = item.route,
                                    )
                                },
                            )
                        }

                    }
                },
            ) {
                NavigationGraph(
                    navController = navController,
                    padding = it,
                    itemRoute = bottomNavItem[0].route,
                )
            }
        }
    }

    @Composable
    private fun NavigationGraph(
        navController: NavHostController,
        padding: PaddingValues,
        itemRoute: String,
    ) {
        NavHost(
            navController,
            startDestination = itemRoute,
            modifier = Modifier.padding(padding),
        ) {
            composable(HomeRoute.DASHBOARD.alias) {
                DashboardMainScreen()

            }
            composable(HomeRoute.BOOKMARK.alias) {
            }

            composable(HomeRoute.EXPLORE.alias) {

            }
            composable(HomeRoute.SETTING.alias) {

            }

        }
    }

}