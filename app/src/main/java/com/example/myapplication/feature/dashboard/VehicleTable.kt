package com.example.myapplication.feature.dashboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun VehicleTable(modifier: Modifier = Modifier) {
    // Hardcoded vehicle data (string arrays)
    val vehicles = listOf(
        arrayOf("Activa 4G", "Honda", "KA 01 AA 0007", "Petrol", "2018", "7 years 6 months"),
        arrayOf("Nexon XM", "Tata", "KA 10 AM 2323", "Petrol", "2021", "4 years 1 month"),
        arrayOf("Activa 125", "Honda", "DL 8 CAF 9876", "Petrol", "2020", "5 years 4 months"),
        arrayOf("Activa 125", "Honda", "DL 8 CAF 9876", "Petrol", "2020", "4 years 9 months"),
        arrayOf("City VX", "Honda", "TN 22 CZ 3344", "Petrol", "2022", "3 years 2 months"),
        arrayOf("Pulsar 150", "Bajaj", "UP 32 KT 1098", "Petrol", "2019", "—"),
        arrayOf("Activa 125", "Honda", "DL 8 CAF 9876", "Petrol", "2020", "5 years 4 months"),
        arrayOf("Activa 125", "Honda", "DL 8 CAF 9876", "Petrol", "2020", "4 years 9 months"),
        arrayOf("City VX", "Honda", "TN 22 CZ 3344", "Petrol", "2022", "3 years 2 months"),
        arrayOf("Pulsar 150", "Bajaj", "UP 32 KT 1098", "Petrol", "2019", "—")
    )

    TableCard(modifier) {

        // Scrollable body:
        LazyColumn {
            items(vehicles.size) { i ->
                val v = vehicles[i]
                VehicleRowItem(
                    model = v[0],
                    brand = v[1],
                    number = v[2],
                    fuel = v[3],
                    year = v[4],
                    subtitle = v[5]
                )
            }
        }
    }
}
@Composable
fun VehicleRowItem(
    model: String,
    brand: String,
    number: String,
    fuel: String,
    year: String,
    subtitle: String
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 14.dp)
            .heightIn(min = 56.dp)
    ) {
        // Model & Brand
        Column(Modifier.weight(0.30f)) {
            Text(model, color = Color(0xFF0A1321), fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(2.dp))
            Text(brand, color = Color(0xFF8C97A6), style = MaterialTheme.typography.bodySmall)
        }

        VDivider()

        // Vehicle Number
        Text(
            number,
            modifier = Modifier.weight(0.26f),
            color = Color(0xFF1F6BFF),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold
        )

        VDivider()

        // Fuel Type
        Text(
            fuel,
            modifier = Modifier.weight(0.18f),
            color = Color(0xFF0A1321),
            style = MaterialTheme.typography.bodyMedium
        )

        VDivider()

        // Year & Subtitle
        Column(Modifier.weight(0.26f)) {
            Text(year, color = Color(0xFF0A1321), style = MaterialTheme.typography.bodyMedium)
            Spacer(Modifier.height(2.dp))
            Text(subtitle, color = Color(0xFF8C97A6), style = MaterialTheme.typography.bodySmall)
        }
    }
    HDivider()
}