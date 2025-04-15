package com.example.pilotoexamen.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.pilotoexamen.data.entities.IncomeEntity
import com.example.pilotoexamen.presentation.viewmodel.IncomeViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IncomeScreen(viewModel: IncomeViewModel) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    var name by remember { mutableStateOf(TextFieldValue("")) }
    var amount by remember { mutableStateOf(TextFieldValue("")) }
    var source by remember { mutableStateOf(TextFieldValue("")) }

    val incomes = remember { mutableStateListOf<IncomeEntity>() }

    LaunchedEffect(Unit) {
        incomes.addAll(viewModel.getAllIncomes())
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Registro de Ingresos") })
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
                value = amount,
                onValueChange = { amount = it },
                label = { Text("Monto") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = source,
                onValueChange = { source = it },
                label = { Text("Fuente") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    val amountValue = amount.text.toDoubleOrNull()
                    if (amountValue != null && name.text.isNotBlank()) {
                        coroutineScope.launch {
                            viewModel.addIncome(
                                name = name.text,
                                amount = amountValue,
                                description = source.text,
                                date = System.currentTimeMillis()
                            )
                            Toast.makeText(context, "Ingreso registrado", Toast.LENGTH_SHORT).show()

                            incomes.clear()
                            incomes.addAll(viewModel.getAllIncomes())

                            name = TextFieldValue("")
                            amount = TextFieldValue("")
                            source = TextFieldValue("")
                        }
                    } else {
                        Toast.makeText(context, "Faltan datos", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Registrar Ingreso")
            }

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(incomes) { income ->
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
                                Text("Nombre: ${income.name}")
                                Text("Monto: ${income.amount}")
                                Text("Fuente: ${income.description}")
                                Text(
                                    "Fecha: ${
                                        SimpleDateFormat(
                                            "dd/MM/yyyy HH:mm",
                                            Locale.getDefault()
                                        ).format(Date(income.date))
                                    }"
                                )
                            }
                            IconButton(onClick = {
                                coroutineScope.launch {
                                    viewModel.deleteIncome(income)
                                    Toast.makeText(context, "Ingreso eliminado", Toast.LENGTH_SHORT).show()
                                    incomes.clear()
                                    incomes.addAll(viewModel.getAllIncomes())
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