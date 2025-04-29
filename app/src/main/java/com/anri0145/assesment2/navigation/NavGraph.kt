package com.anri0145.assesment2.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.anri0145.assesment2.screen.MainScreen

@Composable
fun SetupNavGraph(navController: NavController = rememberNavController()) {
   NavHost(
       navController = navController,
       startDestination = Screen.Home.route
   ){
       composeable(route = Screen.Home.route){
           MainScreen()
       }
   }
}