package com.example.calculadoragorjeta

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.calculadoragorjeta.components.AmountInput
import com.example.calculadoragorjeta.components.TipSlider
import com.example.calculadoragorjeta.components.TipResult
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.example.calculadoragorjeta.ui.theme.CalculadoraGorjetaTheme
import kotlin.math.round

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculadoraGorjetaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    TipCalculatorScreen()
                }
            }
        }
    }
}

class TipViewModel : ViewModel() {
    var amount by mutableStateOf("")
        private set

    var customTipPercentage by mutableStateOf(18f)
        private set

    var errorMessage by mutableStateOf("")
        private set

    fun updateAmount(newAmount: String) {
        amount = newAmount
        errorMessage = if (newAmount.isBlank()) "Insira um valor válido" else ""
    }

    fun updateCustomTip(percentage: Float) {
        customTipPercentage = percentage
    }

    fun calculateTip(amount: Double, tipPercentage: Float): Double {
        return round((amount * tipPercentage / 100) * 100) / 100
    }
}

@Composable
fun TipCalculatorScreen(viewModel: TipViewModel = viewModel()) {
    val amount = viewModel.amount
    val customTipPercentage = viewModel.customTipPercentage
    val errorMessage = viewModel.errorMessage

    val billAmount = amount.toDoubleOrNull() ?: 0.0
    val tip15 = viewModel.calculateTip(billAmount, 15f)
    val tipCustom = viewModel.calculateTip(billAmount, customTipPercentage)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Text(
            text = "Calculadora de Gorjeta",
            style = MaterialTheme.typography.headlineSmall,
            fontSize = 22.sp
        )

        AmountInput(amount) { viewModel.updateAmount(it) }

        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = Color.Red,
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        TipSlider(customTipPercentage) { viewModel.updateCustomTip(it) }

        TipResult(billAmount, tip15, customTipPercentage, tipCustom)
    }
}
