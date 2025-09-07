package com.example.myapplication.feature.addvehicle

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddVehicleScreen(
    onBack: () -> Unit,
    onSubmit: () -> Unit
) {
    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = { Text("Add Vehicle", fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        bottomBar = {
            Button(
                onClick = onSubmit,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1F6BFF)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Add Vehicle", color = Color.White)
            }
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 20.dp)
                .background(Color.White)
        ) {
            Text(
                "Vehicle Details",
                style = MaterialTheme.typography.labelMedium,
                color = Color(0xFF8C97A6),
                modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
            )

            // Brand dropdown
            var brand by remember { mutableStateOf("") }
            OutlinedTextField(
                value = brand,
                onValueChange = { brand = it },
                label = { Text("Brand") },
                placeholder = { Text("Select a brand") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            // Model
            var model by remember { mutableStateOf("") }
            OutlinedTextField(
                value = model,
                onValueChange = { model = it },
                label = { Text("Model") },
                placeholder = { Text("Select a model") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            // Fuel type
            var fuel by remember { mutableStateOf("") }
            OutlinedTextField(
                value = fuel,
                onValueChange = { fuel = it },
                label = { Text("Fuel Type") },
                placeholder = { Text("Select fuel type") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            // Vehicle number
            var number by remember { mutableStateOf("") }
            OutlinedTextField(
                value = number,
                onValueChange = { number = it },
                label = { Text("Vehicle Number") },
                placeholder = { Text("Enter vehicle number (e.g., MH 12 AB 1234)") },
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                "Other Details",
                style = MaterialTheme.typography.labelMedium,
                color = Color(0xFF8C97A6),
                modifier = Modifier.padding(top = 20.dp, bottom = 8.dp)
            )

            // Year of purchase
            var year by remember { mutableStateOf("") }
            OutlinedTextField(
                value = year,
                onValueChange = { year = it },
                label = { Text("Year of Purchase") },
                placeholder = { Text("Select year of purchase") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            // Owner name
            var owner by remember { mutableStateOf("") }
            OutlinedTextField(
                value = owner,
                onValueChange = { owner = it },
                label = { Text("Owner Name") },
                placeholder = { Text("Enter owner's full name") },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}