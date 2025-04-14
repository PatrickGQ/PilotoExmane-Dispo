package com.example.pilotoexamen.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.pilotoexamen.data.entities.ExpenseEntity
import com.example.pilotoexamen.presentation.viewmodel.ExpenseViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun ExpenseScreen(viewModel: ExpenseViewModel) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var name by remember { mutableStateOf(TextFieldValue("")) }
    var price by remember { mutableStateOf(TextFieldValue("")) }
    var description by remember { mutableStateOf(TextFieldValue("")) }

    val expenses = remember { mutableStateListOf<ExpenseEntity>() }

    LaunchedEffect(Unit) {
        expenses.addAll(viewModel.getAllExpenses())
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Registro de Gastos") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = price,
                onValueChange = { price = it },
                label = { Text("Precio") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Descripción") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    val priceValue = price.text.toDoubleOrNull()
                    if (priceValue != null && name.text.isNotBlank()) {
                        coroutineScope.launch {
                            viewModel.addExpense(
                                name = name.text,
                                price = priceValue,
                                description = description.text,
                                date = System.currentTimeMillis()
                            )
                            Toast.makeText(context, "Gasto registrado", Toast.LENGTH_SHORT).show()

                            expenses.clear()
                            expenses.addAll(viewModel.getAllExpenses())

                            name = TextFieldValue("")
                            price = TextFieldValue("")
                            description = TextFieldValue("")
                        }
                    } else {
                        Toast.makeText(context, "Faltan datos", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrar Gasto")
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(expenses) { expense ->
                    Card(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text("Nombre: ${expense.name}")
                                Text("Precio: ${expense.price}")
                                Text("Descripción: ${expense.description}")
                                Text(
                                    "Fecha: ${
                                        SimpleDateFormat(
                                            "dd/MM/yyyy HH:mm",
                                            Locale.getDefault()
                                        ).format(Date(expense.date))
                                    }"
                                )
                            }
                            IconButton(onClick = {
                                coroutineScope.launch {
                                    viewModel.deleteExpense(expense)
                                    Toast.makeText(context, "Gasto eliminado", Toast.LENGTH_SHORT)
                                        .show()
                                    expenses.clear()
                                    expenses.addAll(viewModel.getAllExpenses())
                                }
                            }) {
                                Icon(Icons.Default.Delete, contentDescription = "Eliminar")
                            }
                        }
                    }
                }
            }
        }
    }
}