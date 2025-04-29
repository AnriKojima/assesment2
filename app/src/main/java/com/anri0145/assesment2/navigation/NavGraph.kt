package com.anri0145.assesment2.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.anri0145.assesment2.screen.DetailScreen
import com.anri0145.assesment2.screen.MainScreen

@Composable
fun SetupNavGraph(navController: NavHostController = rememberNavController()) {
   NavHost(
       navController = navController,
       startDestination = Screen.Home.route
   ){
       composable(route = Screen.Home.route){
           MainScreen()
       }
       composable(route = Screen.FormBaru.route){
           DetailScreen()
       }
   }
}