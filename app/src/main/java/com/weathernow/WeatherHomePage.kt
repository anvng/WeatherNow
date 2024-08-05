package com.weathernow

import android.widget.Space
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.weathernow.api.NetworkResponse
import com.weathernow.api.WeatherModel

@Composable
fun WeatherHomePage(weatherViewModel: WeatherViewModel) {
    var city: String by remember { mutableStateOf("") }
    val weatherData = weatherViewModel.weatherData.observeAsState()
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(9.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = city,
                onValueChange = { city = it },
                label = {
                    Text(text = "Search for a city")
                })
            IconButton(onClick = {
                weatherViewModel.getWeather(city)
                keyboardController?.hide()
            }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )

            }
        }

        when(val result = weatherData.value) {
            is NetworkResponse.Error -> {
                Text(text = result.message)
            }

            NetworkResponse.Loading -> {
                CircularProgressIndicator()

            }

            is NetworkResponse.Success -> {
                Text(text = result.data.toString())
            }
            null -> {}
            }
        }
    }
@Composable
fun WeatherDetails(data: WeatherModel){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Bottom){

            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = "Location ic",
                modifier = Modifier.size(30.dp)
            )
            Text(text = data.location.name, fontSize = 20.sp)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = data.location.country, fontSize = 20.sp, color = Color.Gray)

        }
        Spacer(modifier = Modifier.height(17.dp))
        Text(
            text = "${data.current.temp_c} *C",
            fontSize = 50.sp,
            color = Color.Blue,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(17.dp))
        Card {
            Column(
                modifier = Modifier.fillMaxWidth(),
            ){
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ){
                    WeatherKeyVal(key = "Humidity", value = "${data.current.humidity}%")
                    WeatherKeyVal(key = "Wind", value = "${data.current.wind_kph} km/h")
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    WeatherKeyVal("UV",data.current.uv)
                    WeatherKeyVal("Participation",data.current.precip_mm+" mm")
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    WeatherKeyVal("Local Time",data.location.localtime.split(" ")[1])
                    WeatherKeyVal("Local Date",data.location.localtime.split(" ")[0])
                }
            }
        }
    }
}

@Composable
fun  WeatherKeyVal(key: String, value: String){
    Column(
        modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text = key, fontWeight = FontWeight.Bold)
        Text(text = value)
    }
}