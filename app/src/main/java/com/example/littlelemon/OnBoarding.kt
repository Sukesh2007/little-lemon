package com.example.littlelemon

import android.content.SharedPreferences
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.edit
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay

@Composable
fun OnBoarding(sharedPreferences: SharedPreferences, navController: NavHostController) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember {mutableStateOf("")}
    var email by remember { mutableStateOf("") }
    var message by remember {mutableStateOf("")}
    var visible by remember { mutableStateOf(false)}

    LaunchedEffect(visible) {
        if(visible) {
            visible = true
            delay(2000L)
            visible = false
        }
    }

    Scaffold(
        topBar = {
            Box(modifier = Modifier.fillMaxWidth().padding(vertical = 15.dp),
                contentAlignment = Alignment.Center){
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Little Lemon Logo",
                    modifier = Modifier.width(200.dp),
                    contentScale = ContentScale.Crop
                )
            }
        },
        bottomBar = {
            Button(onClick = {
                if(!firstName.isBlank() && !lastName.isBlank() && !email.isBlank()){
                    sharedPreferences.edit(commit = true) { putString("First_Name", firstName) }
                    sharedPreferences.edit(commit = true){ putString("Last_Name", lastName)}
                    sharedPreferences.edit(commit = true){ putString("Email", email)}
                    sharedPreferences.edit(commit = true) { putBoolean("isLoggedIn", true) }
                    navController.navigate(Home.route)
                }else{
                    message = "UnSuccessful login, Enter all the details"
                    visible = true
                }
            },
                colors = ButtonDefaults.buttonColors(Color(0xFFF5CE14)),
                modifier = Modifier.padding(horizontal = 15.dp, vertical = 15.dp).fillMaxWidth()) {
                Text(text = "Register", color = Color.Black, fontSize = 20.sp)
            }
        }
    ){it->
        Column(modifier = Modifier.fillMaxSize().padding(it)){
            Box(modifier = Modifier.fillMaxWidth()
                .height(100.dp)
                .background(color = Color(0xFF495E57)),
                contentAlignment = Alignment.Center){
                Text(
                    text = "Let's get to know you",
                    color = Color.White,
                    fontSize = 30.sp,
                    fontFamily = FontFamily.SansSerif
                )
            }
            Text(
                text = "Personal Information",
                color = Color.Black,
                fontSize = 24.sp,
                fontWeight = Bold,
                fontFamily = FontFamily.SansSerif,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 30.dp)
            )
            Text(
                text= "First Name",
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                modifier = Modifier.padding(horizontal = 10.dp , vertical = 5.dp)
            )
            TextField(
                value = firstName,
                onValueChange = {it-> firstName = it},
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp).fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp)),
                colors = TextFieldDefaults.colors(Color.White)
            )
            Text(
                text= "Last Name",
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                modifier = Modifier.padding(start = 10.dp , top = 30.dp, bottom = 5.dp)
            )
            TextField(
                value = lastName,
                onValueChange = {it-> lastName = it},
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp).fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp)),
                colors = TextFieldDefaults.colors(Color.White)
            )
            Text(
                text= "Email",
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
                modifier = Modifier.padding(start = 10.dp , top = 30.dp, bottom = 5.dp)
            )
            TextField(
                value = email,
                onValueChange = {it-> email = it},
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp).fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp)).border(width = 0.8.dp, color = Color.Gray),
                colors = TextFieldDefaults.colors(Color.White),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
            AnimatedVisibility(
                visible = visible,
                enter = fadeIn(animationSpec = tween(600)),
                exit = fadeOut(animationSpec = tween(600))
            ) {
                Text(
                    text = message,
                    color = Color.Red,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 22.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp).fillMaxWidth()
                )
            }
        }

    }
}



