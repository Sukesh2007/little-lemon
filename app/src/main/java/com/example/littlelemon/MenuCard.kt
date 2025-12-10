package com.example.littlelemon

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import java.nio.file.WatchEvent


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuCard(title: String, description: String, price: String, image: String){
    Column(modifier = Modifier.fillMaxWidth().padding(10.dp)){
        Text(
            text = title,
            color = Color.Black,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp)
        )

        Row(modifier = Modifier.padding(horizontal = 10.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween){
            Column(modifier = Modifier.padding(vertical = 6.dp).fillMaxWidth(0.7f),
                ){
                    Text(
                        text = description,
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(bottom = 5.dp)
                    )
                    Text(
                        text = "$$price",
                        color = Color.DarkGray,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,

                    )
            }
            GlideImage(
                model = image,
                contentDescription = "",
                modifier = Modifier.padding(start = 5.dp).fillMaxWidth().aspectRatio(1f).requiredWidth(90.dp),
                contentScale = ContentScale.Crop
            )
        }
    }
}