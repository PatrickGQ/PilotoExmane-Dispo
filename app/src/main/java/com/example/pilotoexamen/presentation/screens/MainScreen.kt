package com.example.pilotoexamen.presentation.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.pilotoexamen.presentation.viewmodel.ExpenseViewModel
import com.example.pilotoexamen.presentation.viewmodel.IncomeViewModel

@Composable
fun MainScreen(
    expenseViewModel: ExpenseViewModel,
    incomeViewModel: IncomeViewModel
) {
    var selectedScreen by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = selectedScreen == 0,
                    onClick = { selectedScreen = 0 },
                    label = { Text("Gastos") },
                    icon = { Icon(Icons.Default.Delete, contentDescription = null) }
                )
                NavigationBarItem(
                    selected = selectedScreen == 1,
                    onClick = { selectedScreen = 1 },
                    label = { Text("Ingresos") },
                    icon = { Icon(Icons.Default.Delete, contentDescription = null) }
                )
                NavigationBarItem(
                    selected = selectedScreen == 2,
                    onClick = { selectedScreen = 2 },
                    label = { Text("Resumen") },
                    icon = { Icon(Icons.Default.List, contentDescription = null) }
                )
            }
        }
    ) { innerPadding ->
        androidx.compose.foundation.layout.Box(modifier = androidx.compose.ui.Modifier.padding(innerPadding)) {
            when (selectedScreen) {
                0 -> ExpenseScreen(viewModel = expenseViewModel)
                1 -> IncomeScreen(viewModel = incomeViewModel)
                2 -> SummaryScreen(
                    expenseViewModel = expenseViewModel,
                    incomeViewModel = incomeViewModel
                )
            }
        }
    }
}