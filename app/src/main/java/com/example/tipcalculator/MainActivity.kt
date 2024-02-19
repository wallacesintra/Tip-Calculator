package com.example.tipcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tipcalculator.ui.theme.TipCalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TipCalculatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TipTimeLayout()
                }
            }
        }
    }
}


//fun calculateTip(
//    amount: Double?,
//    tipPercent: Double?,
//    roundUp: Boolean
//): String {
//    var tip = tipPercent/100 * amount!!
//    if (roundUp){
//        tip = kotlin.math.ceil(tip)
//    }
//    return NumberFormat.getCurrencyInstance().format(tip)
//}

@Composable
fun EditNumberField(
    label: String,
    value: String,
    @DrawableRes leadingIcon: Int,
    valueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        leadingIcon = { Icon(painter = painterResource(id = leadingIcon), null)},
        singleLine = true,
        label = { Text(label)},
        value = value,
        onValueChange = valueChange,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = modifier
    )
}

@Composable
fun RoundTheTipRow(
    roundUp: Boolean,
    onRoundUpChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier
){
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .size(48.dp)
    ){
        Text(text = stringResource(id = R.string.round_up_tip))
        Switch(
            checked = roundUp,
            onCheckedChange = onRoundUpChanged,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End)

        )
    }
}

@Composable
fun TipTimeLayout(modifier: Modifier = Modifier){
    val viewModel = viewModel<TipViewModel>()
    val state = viewModel.state
//    var amountInput by remember { mutableStateOf("") }
//    var percentTip by remember{ mutableStateOf("15")}
//    val percent = percentTip.toDoubleOrNull() ?: 0.0
//    val amount = amountInput.toDoubleOrNull() ?: 0.0


//    var roundUp by remember { mutableStateOf(false)}

//    val tip = calculateTip(amount,percent,roundUp)
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .statusBarsPadding()
            .padding(horizontal = 40.dp)
            .verticalScroll(rememberScrollState())
    ){
        Text(
            text = stringResource(id = R.string.calculate_tip),
            modifier = Modifier
                .padding(bottom = 16.dp, top = 40.dp)
                .align(alignment = Alignment.Start)
        )
        //Bill Amount
        EditNumberField(
            leadingIcon = R.drawable.money,
            label = stringResource(id = R.string.bill_amount),
            valueChange = { viewModel.onBillAmountChange(it) },
            value= if (state.billAmount != null) state.billAmount.toString() else "",
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth()
        )
       //Tip percent
        EditNumberField(
            leadingIcon = R.drawable.percent,
            label = stringResource(R.string.how_was_the_service),
            value = state.tipPercent.toString(),
            valueChange = { viewModel.onTipPercentChange(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
        )

        RoundTheTipRow(
            roundUp = state.roundTip,
            onRoundUpChanged = {viewModel.onRoundChange(it)},
            modifier = Modifier.padding(bottom = 32.dp)
        )
        Text(
            text = stringResource(id = R.string.tip_amount,state.tip),
            style = MaterialTheme.typography.displaySmall
        )

        Spacer(modifier = Modifier.height(150.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TipCalculatorTheme {
        TipTimeLayout()
    }
}