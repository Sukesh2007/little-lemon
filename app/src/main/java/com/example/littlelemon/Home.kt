package com.example.littlelemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


@Composable
fun Home(navController: NavHostController) {
    var search by remember { mutableStateOf("") }
    var menu by remember { mutableStateOf<List<MenuItemNetwork>>(listOf()) }
    LaunchedEffect(Unit){
        menu = getMenu()
    }
    var selectionIndex by rememberSaveable { mutableIntStateOf(0)}
    val context = LocalContext.current
    val inventoryDatabase = remember{ InventoryDatabase(context) }
    LaunchedEffect(menu) {
        withContext(Dispatchers.IO) {
            menu.forEach { it ->
                val item = Menu(it.id, it.title, it.description, it.price, it.image, it.category)

                inventoryDatabase.updateMenuItem(item)
            }
        }
    }

    var itemsMenu = remember { mutableStateListOf<Menu>()}


    val menuDatabase by inventoryDatabase.getFoodMenu().observeAsState(emptyList())

    val dishVariety = listOf(
        "Starters", "Desserts" , "Mains"
    )

    LaunchedEffect(menuDatabase, search) {
        itemsMenu.clear()

        if (search.isNotBlank()) {
            itemsMenu.addAll(
                menuDatabase.filter { item ->
                    item.title.contains(search, ignoreCase = true)
                }
            )
        } else {
            itemsMenu.addAll(menuDatabase)
        }
    }
    Scaffold(
        topBar = {
            UpperPanel(navController)
        }
    ){it->
        Column {
            MiddlePanel(it, { search = it}, search)

            Text("Order for Delivery",
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                modifier = Modifier.padding(vertical = 12.dp, horizontal = 6.dp))

            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround){
                dishVariety.forEachIndexed { index , it->
                    Button(onClick = {
                        selectionIndex = index
                    },
                        colors = ButtonDefaults.buttonColors(Color.LightGray),
                        modifier = Modifier.clip(RoundedCornerShape(8.dp)).padding(horizontal = 10.dp, vertical = 5.dp),
                        ){
                        Text(
                            text = it,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.Black
                        )
                    }
                }
            }
            LazyColumn() {
                items(itemsMenu.filter {dishVariety[selectionIndex].lowercase() == it.category}) {it->
                        MenuCard(it.title, it.description, it.price, it.image)
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = Color.LightGray,
                        modifier = Modifier.padding(vertical = 10.dp).fillMaxWidth()
                    )
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun HomePreview(){
//    val dishVariety = listOf(
//        "Starters", "Desserts" , "Mains"
//    )
//    Scaffold(
//        topBar = {
//            UpperPanel()
//        }
//    ){it->
//        Column {
//            MiddlePanel(it)
//
//            Text("Order for Delivery",
//                fontWeight = FontWeight.Bold,
//                fontSize = 22.sp,
//                modifier = Modifier.padding(vertical = 12.dp, horizontal = 6.dp))
//
//            Row(modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceAround){
//                dishVariety.forEach {
//                    Box(modifier = Modifier.height(30.dp).clip(RoundedCornerShape(8.dp)).background(Color.LightGray).width(100.dp).padding(horizontal = 10.dp, vertical = 5.dp),
//                        contentAlignment = Alignment.Center){
//                        Text(
//                            text = it,
//                            fontWeight = FontWeight.SemiBold,
//                            color = Color(0xFF495E57)
//                        )
//                    }
//                }
//            }
//            MenuCard()
//
//            MenuCard()
//        }
//    }
//}


@Composable
fun UpperPanel(navController: NavHostController){
    Row(modifier = Modifier.padding(vertical = 10.dp).fillMaxWidth().padding(horizontal = 10.dp).height(80.dp)){
        Image(
            modifier = Modifier.padding(top = 10.dp).fillMaxWidth(0.8f).padding(start = 50.dp).width(150.dp),
        painter = painterResource(R.drawable.logo),
            contentDescription = "LittleLemon Logo",
            contentScale = ContentScale.Crop)
        IconButton(onClick ={navController.navigate(Profile.route)},
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 22.dp).fillMaxWidth()) {
            Image(
                modifier = Modifier.size(110.dp).clip(
                    RoundedCornerShape(55.dp)
                ),
                painter = painterResource(R.drawable.profile),
                contentDescription = "profile photo",
                contentScale = ContentScale.Crop
            )
        }
        }

    }


@Composable
fun MiddlePanel(paddingValues: PaddingValues, onType: (String) -> Unit, value: String){

    Column(modifier = Modifier.padding(paddingValues).background(Color(0xFF495E57)).padding(10.dp)){
        Text(text = "Little Lemon",
            color = Color(0XFFF5CE14),
            fontWeight = FontWeight.Bold,
            fontSize = 42.sp,
            modifier = Modifier.padding(start = 5.dp)
        )
        Row(modifier = Modifier.fillMaxWidth().padding(start = 5.dp, top = 12.dp)){
            Column(modifier = Modifier.fillMaxWidth(0.6f)){
                Text(
                    text = "Chicago",
                    color = Color.White,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 28.sp,
                    modifier = Modifier.padding(top = 5.dp, bottom = 15.dp)
                )
                Text(text = "We are a family owned " +
                        "Mediterranean restaurant, " +
                        "focused on traditional " +
                        "recipes served with a " +
                        "modern twist.",
                    color = Color.White,
                    fontSize = 18.sp,
                    )
            }
            Box(modifier = Modifier.padding(vertical = 20.dp).fillMaxWidth(),
                contentAlignment = Alignment.Center) {
                Image(
                    modifier = Modifier.aspectRatio(1f).requiredWidth(120.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    painter = painterResource(R.drawable.hero),
                    contentDescription = "Service",
                    contentScale = ContentScale.Crop
                )
            }
        }
        TextField(
            value = value,
            onValueChange = { onType(it)},
            modifier = Modifier.padding(horizontal = 5.dp).fillMaxWidth().padding(10.dp),
            leadingIcon = {Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "search bar",
                modifier = Modifier.padding(10.dp)
            )},
            placeholder = {
                Text(
                    text = "Enter Search Phrase",
                    fontFamily = FontFamily.Monospace,
                    fontSize = 20.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.ExtraLight
                )
            }
        )

    }
}
