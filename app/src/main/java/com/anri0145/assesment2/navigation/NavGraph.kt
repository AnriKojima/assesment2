package com.anri0145.assesment2.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.anri0145.assesment2.screen.DetailScreen
import com.anri0145.assesment2.screen.KEY_ID_PENGELUARAN
import com.anri0145.assesment2.screen.MainScreen
import com.anri0145.assesment2.screen.RecycleBinScreen

@Composable
fun SetupNavGraph(navController: NavHostController = rememberNavController()) {
   NavHost(
       navController = navController,
       startDestination = Screen.Home.route
   ){

           composable(route = Screen.Home.route){
               MainScreen(navController)
           }
           composable(route = Screen.Main.route){
               MainScreen(navController)
           }
           composable(route = Screen.FormBaru.route){
               DetailScreen(navController)
           }
           composable(
               route = Screen.FormUbah.route,
               arguments = listOf(
                   navArgument(KEY_ID_PENGELUARAN) {type = NavType.LongType}
               )
           ){ navBackStackEntry ->
               val id = navBackStackEntry.arguments?.getLong(KEY_ID_PENGELUARAN)
               DetailScreen(navController, id)
           }
           composable(route = Screen.Recycle.route,
           ) {
               RecycleBinScreen(navController)
           }
       }
   }
