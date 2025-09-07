package com.example.myapplication.feature.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.myapplication.R

@Composable
fun DashboardScreen(
    userName: String,
    avatarRes: Int,
    onAddVehicleClick: () -> Unit
) {
    val showFilter = remember { mutableStateOf(false) }

    // Show filter dialog when needed
    if (showFilter.value) {
        FilterDialog(
            onDismiss = { showFilter.value = false },
            onApply = { showFilter.value = false }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF0F3F7))
    ) {
        // 👉 Main vertical content
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Greeting header
            GreetingHeader(
                userName = userName,
                avatarRes = avatarRes
            )

            // Section title
            SectionHeader("Vehicle Inventory List")

            // Filter pill row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                FilterPill(
                    text = "Filter",
                    iconRes = R.drawable.outline_filter_alt_24,
                    modifier = Modifier.clickable { showFilter.value = true }
                )
            }

            // Vehicle table (scrollable)
            VehicleTable(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = 88.dp) // keep space for FAB
            )
        }


        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 20.dp, bottom = 20.dp)
        ) {
            AddVehicleFab(
                onClick = onAddVehicleClick
            )
        }
    }
}


@Composable
fun AddVehicleFab(onClick: () -> Unit) {
    ExtendedFloatingActionButton(
        onClick = onClick,
        icon = {
            Image(
                painter = painterResource(R.drawable.baseline_add_24),
                contentDescription = "Add Vehicle"
            )
        },
        text = {
            Text(
                text = "Add Vehicle",
                style = MaterialTheme.typography.labelLarge,
                color = Color.White
            )
        },
        containerColor = Color(0xFF1F6BFF),
        contentColor = Color.White,
        shape = RoundedCornerShape(16.dp),
        elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 6.dp)
    )
}