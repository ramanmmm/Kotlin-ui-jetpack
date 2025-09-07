package com.example.myapplication.feature.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.myapplication.design.ColorTokens.Surface
import kotlinx.coroutines.flow.MutableSharedFlow

@Composable
fun FilterDialog(
    onDismiss: () -> Unit,
    onApply: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(22.dp),
            color = Color.White,
            tonalElevation = 2.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(bottom = 0.dp) // remove extra padding
            ) {
                // Top bar
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Filter",
                        style = MaterialTheme.typography.titleLarge,
                        color = Color(0xFF0A1321),
                        modifier = Modifier.weight(1f)
                    )
                    Text(
                        "✕",
                        modifier = Modifier
                            .clickable { onDismiss() }
                            .padding(4.dp),
                        color = Color(0xFF0A1321)
                    )
                }

                DividerThin()

                // Two-pane body (scrollable area)
                FilterBody(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 380.dp)
                        .weight(1f, fill = true)   // body takes all space above buttons
                        .padding(top = 8.dp)
                )

                DividerThin()

                // Bottom actions pinned
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = { filterBus.tryEmit(FilterIntent.ClearAll) },
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color(0xFF0A1321)
                        )
                    ) {
                        Text("Clear all")
                    }

                    Button(
                        onClick = onApply,
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1F6BFF))
                    ) {
                        Text("Apply", color = Color.White)
                    }
                }
            }
        }
    }
}
private enum class FilterTab { Brand, Fuel }

private val filterBus = MutableSharedFlow<FilterIntent>(extraBufferCapacity = 1)
private sealed interface FilterIntent { object ClearAll : FilterIntent }

@Composable
private fun FilterBody(modifier: Modifier = Modifier) {
    // Hard-coded options
    val brands = listOf("Tata", "Honda", "Hero", "Bajaj", "Yamaha", "Other")
    val fuels  = listOf("Petrol", "Electric", "Diesel", "CNG")

    var selectedTab by remember { mutableStateOf(FilterTab.Brand) }
    val selectedBrands = remember { mutableStateListOf<String>() }
    val selectedFuels  = remember { mutableStateListOf<String>() }

    // react to ClearAll button
    LaunchedEffect(Unit) {
        filterBus.collect { intent ->
            if (intent is FilterIntent.ClearAll) {
                selectedBrands.clear()
                selectedFuels.clear()
            }
        }
    }

    Row(
        modifier = modifier
            .background(Color(0xFFF6F9FC), RoundedCornerShape(16.dp))
            .padding(4.dp)
    ) {
        // Left side tabs
        Column(
            Modifier
                .width(140.dp)
                .fillMaxHeight()
                .background(Color.White, RoundedCornerShape(12.dp))
        ) {
            FilterTabItem(
                title = "Brand",
                selected = selectedTab == FilterTab.Brand,
                onClick = { selectedTab = FilterTab.Brand }
            )
            DividerThin()
            FilterTabItem(
                title = "Fuel Type",
                selected = selectedTab == FilterTab.Fuel,
                onClick = { selectedTab = FilterTab.Fuel }
            )
        }

        // Right side list
        Column(
            Modifier
                .weight(1f)
                .padding(start = 12.dp)
                .background(Color(0xFFF6FAFF), RoundedCornerShape(12.dp))
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                val items = if (selectedTab == FilterTab.Brand) brands else fuels
                items(items.size) { i ->
                    val label = items[i]
                    FilterCheckRow(
                        text = label,
                        checked = if (selectedTab == FilterTab.Brand)
                            selectedBrands.contains(label) else selectedFuels.contains(label),
                        onCheckedChange = { v ->
                            if (selectedTab == FilterTab.Brand) {
                                if (v) selectedBrands.add(label) else selectedBrands.remove(label)
                            } else {
                                if (v) selectedFuels.add(label) else selectedFuels.remove(label)
                            }
                        }
                    )
                    DividerHairline()
                }
            }
        }
    }
}

@Composable
private fun FilterTabItem(title: String, selected: Boolean, onClick: () -> Unit) {
    val color = if (selected) Color(0xFF1F6BFF) else Color(0xFF0A1321)
    val weight = if (selected) FontWeight.SemiBold else FontWeight.Normal
    Text(
        text = title,
        color = color,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = weight,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 18.dp)
    )
}

@Composable private fun DividerThin() =
    Box(Modifier.fillMaxWidth().height(1.dp).background(Color(0xFFE6EBF2)))

@Composable private fun DividerHairline() =
    Box(Modifier.fillMaxWidth().height(0.6.dp).background(Color(0xFFEFF3F8)))

@Composable
private fun FilterCheckRow(
    text: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            color = Color(0xFF0A1321),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f)
        )
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                checkedColor = Color(0xFF1F6BFF),
                uncheckedColor = Color(0xFFB9C3D0)
            )
        )
    }
}
