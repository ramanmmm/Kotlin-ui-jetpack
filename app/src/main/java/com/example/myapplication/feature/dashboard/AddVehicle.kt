package com.example.myapplication.feature.addvehicle

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material.icons.filled.LocalGasStation
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.myapplication.R

// ------------ Data models ------------
data class VehicleBrand(val name: String, val iconRes: Int? = null)

// Put your brand icons in res/drawable (lowercase names)
private val vehicleBrands = listOf(
    VehicleBrand("Tata",   R.drawable.tata),
    VehicleBrand("Honda",  R.drawable.honda),
    VehicleBrand("Hero",   R.drawable.hero),
    VehicleBrand("Bajaj",  R.drawable.bajaj),
    VehicleBrand("Yamaha", R.drawable.yamaha),
    VehicleBrand("Other",  null)
)

// Brand -> Models map (sample). Edit as per your data.
private val modelsByBrand: Map<String, List<String>> = mapOf(
    "Honda" to listOf(
        "Activa 4G",
        "Activa 5G",
        "Activa 6G",
        "Activa 125",
        "Activa 125 BS6",
        "Activa H-Smart"
    ),
    "Hero" to listOf("Splendor Plus", "HF Deluxe", "Passion Pro"),
    "Bajaj" to listOf("Pulsar 150", "Pulsar N160", "CT 100"),
    "Yamaha" to listOf("FZ-S V3", "MT-15", "R15 V4"),
    "Tata" to listOf("Tiago", "Altroz", "Punch", "Nexon"),
    "Other" to listOf("Custom / Not Listed")
)

// ------------ Fuel Types ------------
private val fuelTypes = listOf(
    "Petrol",
    "Diesel",
    "CNG",
    "LPG",
    "Electric",
    "Hybrid (Petrol + Electric)",
    "Other"
)

// ------------ Brand Picker ------------
@Composable
private fun BrandPickerField(
    label: String = "Brand",
    selected: VehicleBrand?,
    onSelected: (VehicleBrand) -> Unit,
    brands: List<VehicleBrand>,
    modifier: Modifier = Modifier
) {
    var open by rememberSaveable { mutableStateOf(false) }

    Box(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = selected?.name ?: "",
            onValueChange = { /* read-only */ },
            label = { Text(label) },
            placeholder = { Text("Select a brand") },
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.DirectionsCar,
                    contentDescription = null,
                    modifier = Modifier.clickable { open = true } // icon click also opens
                )
            }
        )
        // invisible overlay to capture clicks anywhere on the field
        Box(
            Modifier
                .matchParentSize()
                .clickable { open = true }
        )
    }

    if (open) {
        AlertDialog(
            onDismissRequest = { open = false },
            title = { Text("Select Vehicle Brand", fontWeight = FontWeight.SemiBold) },
            confirmButton = {},
            text = {
                LazyColumn(Modifier.fillMaxWidth().heightIn(max = 420.dp)) {
                    items(brands) { brand ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                                .background(Color(0xFFF7F9FC), RoundedCornerShape(12.dp))
                                .clickable {
                                    onSelected(brand); open = false
                                }
                                .padding(horizontal = 14.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (brand.iconRes != null) {
                                Image(
                                    painter = painterResource(brand.iconRes),
                                    contentDescription = brand.name,
                                    modifier = Modifier.size(28.dp)
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Filled.DirectionsCar,
                                    contentDescription = brand.name,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                            Spacer(Modifier.width(12.dp))
                            Text(brand.name, style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f))
                            RadioButton(
                                selected = selected?.name == brand.name,
                                onClick = { onSelected(brand); open = false }
                            )
                        }
                        Spacer(Modifier.height(8.dp))
                    }
                }
            }
        )
    }
}
// ------------ Model Picker (filtered by brand) ------------
@Composable
private fun ModelPickerField(
    label: String = "Model",
    brand: VehicleBrand?,
    selectedModel: String?,
    onSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var open by rememberSaveable { mutableStateOf(false) }
    val models = modelsByBrand[brand?.name] ?: emptyList()
    val enabled = brand != null && models.isNotEmpty()

    Box(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = selectedModel.orEmpty(),
            onValueChange = { /* read-only */ },
            label = { Text(label) },
            placeholder = { Text(if (brand == null) "Select brand first" else "Select a model") },
            readOnly = true,
            enabled = enabled,
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.ListAlt,
                    contentDescription = null,
                    modifier = Modifier
                        .clickable(enabled = enabled) { open = true }
                )
            }
        )
        Box(
            Modifier
                .matchParentSize()
                .clickable(enabled = enabled) { open = true }
        )
    }

    if (open) {
        AlertDialog(
            onDismissRequest = { open = false },
            title = { Text("Select Vehicle Model", fontWeight = FontWeight.SemiBold) },
            confirmButton = {},
            text = {
                LazyColumn(Modifier.fillMaxWidth().heightIn(max = 420.dp)) {
                    items(models) { model ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                                .background(Color(0xFFF7F9FC), RoundedCornerShape(12.dp))
                                .clickable { onSelected(model); open = false }
                                .padding(horizontal = 14.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Spacer(Modifier.width(4.dp))
                            Text(model, style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f))
                            RadioButton(
                                selected = selectedModel == model,
                                onClick = { onSelected(model); open = false }
                            )
                        }
                        Spacer(Modifier.height(8.dp))
                    }
                }
            }
        )
    }
}
// ------------ Fuel Picker ------------
@Composable
private fun FuelPickerField(
    label: String = "Fuel Type",
    selectedFuel: String?,
    onSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var open by rememberSaveable { mutableStateOf(false) }

    Box(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = selectedFuel.orEmpty(),
            onValueChange = { /* read-only */ },
            label = { Text(label) },
            placeholder = { Text("Select fuel type") },
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Filled.LocalGasStation,
                    contentDescription = null,
                    modifier = Modifier.clickable { open = true }
                )
            }
        )
        Box(
            Modifier
                .matchParentSize()
                .clickable { open = true }
        )
    }

    if (open) {
        AlertDialog(
            onDismissRequest = { open = false },
            title = { Text("Select Fuel Type", fontWeight = FontWeight.SemiBold) },
            confirmButton = {},
            text = {
                LazyColumn(Modifier.fillMaxWidth().heightIn(max = 420.dp)) {
                    items(fuelTypes) { fuel ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                                .background(Color(0xFFF7F9FC), RoundedCornerShape(12.dp))
                                .clickable { onSelected(fuel); open = false }
                                .padding(horizontal = 14.dp, vertical = 12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Spacer(Modifier.width(4.dp))
                            Text(fuel, style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f))
                            RadioButton(
                                selected = selectedFuel == fuel,
                                onClick = { onSelected(fuel); open = false }
                            )
                        }
                        Spacer(Modifier.height(8.dp))
                    }
                }
            }
        )
    }
}

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
                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
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

            // Brand & Model
            var selectedBrand by rememberSaveable { mutableStateOf<VehicleBrand?>(null) }
            var selectedModel by rememberSaveable { mutableStateOf<String?>(null) }

            BrandPickerField(
                selected = selectedBrand,
                onSelected = { brand ->
                    selectedBrand = brand
                    selectedModel = null // clear model when brand changes
                },
                brands = vehicleBrands
            )

            Spacer(Modifier.height(12.dp))

            ModelPickerField(
                brand = selectedBrand,
                selectedModel = selectedModel,
                onSelected = { selectedModel = it }
            )

            Spacer(Modifier.height(12.dp))

            // Fuel Type (NEW)
            var selectedFuel by rememberSaveable { mutableStateOf<String?>(null) }
            FuelPickerField(
                selectedFuel = selectedFuel,
                onSelected = { selectedFuel = it }
            )

            Spacer(Modifier.height(12.dp))

            // Vehicle number
            var number by rememberSaveable { mutableStateOf("") }
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

            var year by rememberSaveable { mutableStateOf("") }
            OutlinedTextField(
                value = year,
                onValueChange = { year = it },
                label = { Text("Year of Purchase") },
                placeholder = { Text("Select year of purchase") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            var owner by rememberSaveable { mutableStateOf("") }
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