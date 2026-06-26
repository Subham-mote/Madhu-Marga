package com.madhumarga.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.madhumarga.app.ui.HiveViewModel
import com.madhumarga.app.ui.screens.FloraCalendarScreen
import com.madhumarga.app.ui.screens.HarvestTrackerScreen
import com.madhumarga.app.ui.screens.HiveDetailScreen
import com.madhumarga.app.ui.screens.HiveRegisterScreen
import com.madhumarga.app.ui.screens.HomeScreen
import com.madhumarga.app.ui.screens.InspectionLogScreen
import com.madhumarga.app.ui.theme.MadhuMargaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MadhuMargaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = androidx.compose.material3.MaterialTheme.colorScheme.background
                ) {
                    AppNavHost()
                }
            }
        }
    }
}

private object Routes {
    const val HOME = "home"
    const val REGISTER = "register"
    const val INSPECTION = "inspection"
    const val HARVEST = "harvest"
    const val FLORA = "flora"
    const val DETAIL = "detail/{hiveId}"
    fun detail(id: Long) = "detail/$id"
}

@Composable
private fun AppNavHost() {
    val nav = rememberNavController()
    val vm: HiveViewModel = viewModel(factory = HiveViewModel.Factory)

    NavHost(navController = nav, startDestination = Routes.HOME) {
        composable(Routes.HOME) {
            HomeScreen(
                vm = vm,
                onOpenHive = { id -> nav.navigate(Routes.detail(id)) },
                onAddHive = { nav.navigate(Routes.REGISTER) },
                onOpenInspection = { nav.navigate(Routes.INSPECTION) },
                onOpenHarvest = { nav.navigate(Routes.HARVEST) },
                onOpenFlora = { nav.navigate(Routes.FLORA) }
            )
        }
        composable(Routes.REGISTER) {
            HiveRegisterScreen(
                vm = vm,
                onSaved = { nav.popBackStack() },
                onBack = { nav.popBackStack() }
            )
        }
        composable(Routes.INSPECTION) {
            InspectionLogScreen(
                vm = vm,
                onSaved = { nav.popBackStack() },
                onBack = { nav.popBackStack() }
            )
        }
        composable(Routes.HARVEST) {
            HarvestTrackerScreen(vm = vm, onBack = { nav.popBackStack() })
        }
        composable(Routes.FLORA) {
            FloraCalendarScreen(onBack = { nav.popBackStack() })
        }
        composable(
            route = Routes.DETAIL,
            arguments = listOf(navArgument("hiveId") { type = NavType.LongType })
        ) { backStackEntry ->
            val hiveId = backStackEntry.arguments?.getLong("hiveId") ?: 0L
            HiveDetailScreen(
                vm = vm,
                hiveId = hiveId,
                onBack = { nav.popBackStack() }
            )
        }
    }
}
