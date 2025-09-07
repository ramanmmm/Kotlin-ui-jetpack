package com.example.myapplication.feature.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.myapplication.R

@Composable
fun GreetingHeader(userName: String, avatarRes: Int, modifier: Modifier = Modifier) {
    val gradient = Brush.verticalGradient(listOf(Color(0xFF0B1630), Color(0xFF0E244F)))

    Box(
        modifier
            .fillMaxWidth()
            .background(gradient)
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            // greeting row
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(avatarRes),
                    contentDescription = null,
                    modifier = Modifier.size(64.dp).clip(CircleShape)
                )
                Spacer(Modifier.width(16.dp))
                Column(Modifier.weight(1f)) {
                    Text("Hi, $userName 👋", color = Color.White,
                        style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.SemiBold)
                    Spacer(Modifier.height(6.dp))
                    Text("Welcome back!", color = Color.White.copy(alpha = 0.85f),
                        style = MaterialTheme.typography.bodyMedium)
                }
            }

            Spacer(Modifier.height(16.dp))

            // cards row (now it sits BELOW the greeting)
            StatsRow(
                leftIcon = R.drawable.baseline_directions_car_filled_24,
                leftValue = "2300",
                leftLabel = "Total Vehicles",
                leftBg = 0xFFF9EDDC,
                rightIcon = R.drawable.baseline_electric_bolt_24,
                rightValue = "2300",
                rightLabel = "Total EV",
                rightBg = 0xFFE9F6EF
            )

            Spacer(Modifier.height(12.dp))
        }
    }
}