package com.example.pilotoexamen.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.pilotoexamen.presentation.viewmodel.ExpenseViewModel
import com.example.pilotoexamen.presentation.viewmodel.IncomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SummaryScreen(
    expenseViewModel: ExpenseViewModel,
    incomeViewModel: IncomeViewModel
) {
    val totalExpenses = remember { mutableStateOf(0.0) }
    val totalIncomes = remember { mutableStateOf(0.0) }

    LaunchedEffect(Unit) {
        val expenses = expenseViewModel.getAllExpenses()
        val incomes = incomeViewModel.getAllIncomes()

        totalExpenses.value = expenses.sumOf { it.price }
        totalIncomes.value = incomes.sumOf { it.amount }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Resumen de Finanzas") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Text(text = "Total Ingresos: Bs ${totalIncomes.value}")
            Text(text = "Total Gastos: Bs ${totalExpenses.value}")
            Text(text = "Saldo Final: Bs ${totalIncomes.value - totalExpenses.value}")
        }
    }
}