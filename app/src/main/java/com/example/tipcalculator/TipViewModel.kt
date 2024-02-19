package com.example.tipcalculator

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.text.NumberFormat

data class TipUiState(
    val billAmount: Double? = null,
    val tip: String = "0.0",
    val tipPercent: Double? = 15.0,
    val roundTip: Boolean = false
)
class TipViewModel: ViewModel() {
    var state by mutableStateOf(TipUiState())
        private set

    private fun calculateTip(billedAmount: Double?, tipPercent: Double?, roundTip: Boolean): String{
        var tip: Double = 0.0
        if (billedAmount != null && tipPercent != null){
            tip = billedAmount * tipPercent/100
            if (roundTip){
                tip = kotlin.math.ceil(tip)
            }
        }
        return NumberFormat.getCurrencyInstance().format(tip)
    }

    fun onBillAmountChange(value: String){
        state = state.copy(
            billAmount = value.toDouble()
        )

            val billAmount = state.billAmount
            val tipPercent = state.tipPercent
            val roundTip = state.roundTip
            state = state.copy(
                tip = calculateTip(billAmount,tipPercent,roundTip)
            )

    }

    fun onTipPercentChange(value: String){
        state = state.copy(
            tipPercent = value.toDouble()
        )
        val billAmount = state.billAmount
        val tipPercent = state.tipPercent
        val roundTip = state.roundTip
        state = state.copy(
            tip = calculateTip(billAmount,tipPercent,roundTip)
        )
    }

    fun onRoundChange(round: Boolean){
        state = state.copy(
            roundTip = !state.roundTip
        )
    }
}