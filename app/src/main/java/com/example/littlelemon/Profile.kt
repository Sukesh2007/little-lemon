package com.example.littlelemon

import android.content.SharedPreferences
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.edit
import androidx.navigation.NavHostController

@Composable
fun Profile(sharedPreferences: SharedPreferences, navController: NavHostController) {
    val firstName = sharedPreferences.getString("First_Name", "")
    val lastName = sharedPreferences.getString("Last_Name", "")
    val email = sharedPreferences.getString("Email", "")
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
            Button(
                onClick= {
                    sharedPreferences.edit(commit = true) { remove("First_Name")}
                    sharedPreferences.edit(commit = true) { remove("Last_Name")}
                    sharedPreferences.edit(commit = true) { remove("Email")}
                    sharedPreferences.edit(commit = true) { putBoolean("isLoggedIn", false) }
                    navController.navigate(OnBoarding.route)

                },
                colors = ButtonDefaults.buttonColors(Color(0xFFF5CE14)),
                modifier = Modifier.padding(horizontal = 15.dp, vertical = 20.dp).fillMaxWidth()
            ) {
                Text(
                    text = "LogOut",
                    color = Color.Black,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold,

                    )
            }
        }
    ){it->
        Box(modifier = Modifier.padding(it).fillMaxWidth()
            .height(100.dp)
            .background(color = Color(0xFF495E57)),
            contentAlignment = Alignment.Center){
            Text(
                text = "Your Profile",
                color = Color.White,
                fontSize = 30.sp,
                fontFamily = FontFamily.SansSerif
            )
        }
        Column(modifier = Modifier.padding(it).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center){

            Text(
                text = "Have a nice day!",
                color = Color.Gray,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(vertical = 20.dp),
                fontSize = 30.sp
            )
            Box(modifier = Modifier.padding(vertical = 20.dp).clip(RoundedCornerShape(45.dp)).size(90.dp)) {
                Image(
                    painter = painterResource(R.drawable.img),
                    contentDescription = "Profile photo",
                    contentScale = ContentScale.Crop,
                )
            }

            Text(
                text = "First Name: $firstName",
                color = Color.Black,
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 15.dp)
            )
            Text(
                text = "Last Name: $lastName",
                color = Color.Black,
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 15.dp)
            )

            Text(
                text = "Email: $email",
                color = Color.Black,
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfilePreview(){
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
            Button(
                onClick= {},
                colors = ButtonDefaults.buttonColors(Color(0xFFF5CE14)),
                modifier = Modifier.padding(horizontal = 15.dp, vertical = 20.dp).fillMaxWidth()
            ) {
                Text(
                    text = "LogOut",
                    color = Color.Black,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.SemiBold,

                )
            }
        }
    ){it->
        Box(modifier = Modifier.padding(it).fillMaxWidth()
            .height(100.dp)
            .background(color = Color(0xFF495E57)),
            contentAlignment = Alignment.Center){
            Text(
                text = "Your Profile",
                color = Color.White,
                fontSize = 30.sp,
                fontFamily = FontFamily.SansSerif
            )
        }
        Column(modifier = Modifier.padding(it).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center){

            Text(
                text = "Have a nice day!",
                color = Color.Gray,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(vertical = 20.dp),
                fontSize = 30.sp
            )
            Box(modifier = Modifier.padding(vertical = 20.dp).clip(RoundedCornerShape(45.dp)).size(90.dp)) {
                Image(
                    painter = painterResource(R.drawable.img),
                    contentDescription = "Profile photo",
                    contentScale = ContentScale.Crop,
                )
            }

            Text(
                text = "Name: xxxxxxx",
                color = Color.Black,
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(bottom = 15.dp)
            )

            Text(
                text = "email: yyyyyyy",
                color = Color.Black,
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
            )
        }
    }
}