package com.example.littlelemon

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class MenuNetworkData(
    val menu: List<MenuItemNetwork>
)

@Serializable
data class MenuItemNetwork(
    val id: Int,
    val title: String,
    val description: String,
    val price: String,
    val image: String,
    val category: String
)

val client: HttpClient = HttpClient(Android){
    install(ContentNegotiation){
        json(
            Json {
                ignoreUnknownKeys = true
            },
            contentType = ContentType("text", "plain")
        )
    }
}

suspend fun getMenu(): List<MenuItemNetwork> {
    val response: MenuNetworkData = client.get("https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json").body()
    return response.menu
}