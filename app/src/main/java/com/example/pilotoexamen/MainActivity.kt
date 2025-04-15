package com.example.pilotoexamen

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.pilotoexamen.data.AppDatabase
import com.example.pilotoexamen.data.repository.ExpenseRepository
import com.example.pilotoexamen.data.repository.IncomeRepository
import com.example.pilotoexamen.presentation.screens.ExpenseScreen
import com.example.pilotoexamen.presentation.screens.IncomeScreen
import com.example.pilotoexamen.presentation.screens.MainScreen
import com.example.pilotoexamen.presentation.viewmodel.ExpenseViewModel
import com.example.pilotoexamen.presentation.viewmodel.ExpenseViewModelFactory
import com.example.pilotoexamen.presentation.viewmodel.IncomeViewModel
import com.example.pilotoexamen.presentation.viewmodel.IncomeViewModelFactory
import com.example.pilotoexamen.ui.theme.PilotoExamenTheme

class MainActivity : ComponentActivity() {

    private lateinit var expenseViewModel: ExpenseViewModel
    private lateinit var incomeViewModel: IncomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
            requestPermissions(
                arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                1001
            )
        }

        com.example.pilotoexamen.util.AppContextProvider.context = applicationContext

        val database = AppDatabase.getDatabase(applicationContext)

        val incomeRepository = IncomeRepository(database.incomeDao())
        val incomeViewModelFactory = IncomeViewModelFactory(incomeRepository)
        incomeViewModel = ViewModelProvider(this, incomeViewModelFactory)[IncomeViewModel::class.java]

        val expenseRepository = ExpenseRepository(database.expenseDao())
        val expenseViewModelFactory = ExpenseViewModelFactory(expenseRepository, incomeViewModel)
        expenseViewModel = ViewModelProvider(this, expenseViewModelFactory )[ExpenseViewModel::class.java]

        setContent {
            PilotoExamenTheme {
                MainScreen(
                    expenseViewModel = expenseViewModel,
                    incomeViewModel = incomeViewModel
                )
            }
        }
    }
}