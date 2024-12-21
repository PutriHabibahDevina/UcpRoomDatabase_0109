package com.example.ucp2.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ucp2.ui.view.dosen.DestinasiInsert
import com.example.ucp2.ui.view.dosen.DetailDosenView
import com.example.ucp2.ui.view.dosen.HomeDosenView
import com.example.ucp2.ui.view.dosen.InsertDosenView

@Composable
fun PengelolaHalamanDosen(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
){
    NavHost(
        navController = navController,
        startDestination = DestinasiDosenHome.route
    ){
        composable(route = DestinasiDosenHome.route)
        {
            HomeDosenView(
                onDetailClick = { nidn ->
                    navController.navigate("${DestinasiDosenDetail.route}/$nidn")
                    println("PengelolaHalamanDosen: nidn = $nidn")
                },
                onAddDosen = {navController.navigate(DestinasiInsert.route)},
                modifier = modifier
            )
        }

        composable(route = DestinasiInsert.route)
        {
            InsertDosenView(
                onBack = {navController.popBackStack()},
                onNavigate = {navController.popBackStack()},
                modifier = modifier
            )
        }

        composable(
            DestinasiDosenDetail.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiDosenDetail.NIDN) {
                    type = NavType.StringType
                }
            )
        ) {
            val nidn = it.arguments?.getString(DestinasiDosenDetail.NIDN)
            nidn?.let { nidn ->
                DetailDosenView(
                    onBack = {navController.popBackStack()},
                    modifier = modifier
                )
            }
        }
    }
}