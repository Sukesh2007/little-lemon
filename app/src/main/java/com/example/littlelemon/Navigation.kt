package com.example.littlelemon

import android.content.SharedPreferences
import androidx.compose.runtime.Composable
import androidx.lifecycle.MutableLiveData
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation(sharedPreferences: SharedPreferences){
    var start : String = ""
    start = if(sharedPreferences.getBoolean("isLoggedIn", false)){
        Home.route
    }else{
        OnBoarding.route
    }

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = start){
        composable(Home.route){
            Home( navController)
        }
        composable(OnBoarding.route){
            OnBoarding(sharedPreferences, navController)
        }
        composable(Profile.route){
            Profile(sharedPreferences, navController)
        }
    }
}