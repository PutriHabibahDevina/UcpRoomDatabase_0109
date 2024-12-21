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
import com.example.ucp2.ui.view.matakuliah.DetailMKView
import com.example.ucp2.ui.view.matakuliah.HomeMKView
import com.example.ucp2.ui.view.matakuliah.InsertMKView
import com.example.ucp2.ui.view.matakuliah.UpdateMKView

@Composable
fun PengelolaHalamanMK(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = DestinasiMKHome.route
    ) {
        composable(route = DestinasiMKHome.route)
        {
            HomeMKView(
                onDetailClick = { kode ->
                    navController.navigate("${DestinasiMKDetail.route}/$kode")
                    println("PengelolaHalamanMK: kode = $kode")
                },
                onAddMK = {navController.navigate(DestinasiInsert.route)},
                modifier = modifier
            )
        }

        composable(route = DestinasiInsert.route)
        {
            InsertMKView(
                onBack = {navController.popBackStack()},
                onNavigate = {navController.popBackStack()},
                modifier = modifier
            )
        }

        composable(
            DestinasiMKDetail.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiMKDetail.KODE) {
                    type = NavType.StringType
                }
            )
        ) {
            val kode = it.arguments?.getString(DestinasiMKDetail.KODE)
            kode?.let { kode ->
                DetailMKView(
                    onBack = {navController.popBackStack()},
                    onEditClick = {navController.navigate("${DestinasiMKUpdate.route}/$it")},
                    modifier = modifier,
                    onDeleteClick = {navController.popBackStack()}
                )
            }
        }

        composable(
            DestinasiMKUpdate.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiMKUpdate.KODE){
                    type = NavType.StringType
                }
            )
        ) {
            UpdateMKView(
                onBack = {navController.popBackStack()},
                onNavigate = {navController.popBackStack()},
                modifier = modifier
            )
        }
    }
}