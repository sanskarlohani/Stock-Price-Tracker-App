import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.stock.Stock
import com.example.stock.StockViewModel

@Composable
fun StockScreen(stockViewModel: StockViewModel = viewModel()) {
    val isLoading by remember { stockViewModel.isLoading }
    val errorMessage by remember { stockViewModel.errorMessage }
    var searchText by remember { mutableStateOf(TextFieldValue("")) }
    val scrollState = rememberScrollState()
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Stock Prices", style = MaterialTheme.typography.headlineSmall)


        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = searchText,
            onValueChange = {
                searchText = it
                stockViewModel.searchText.value = it.text // Update search query
            },
            label = { Text("Search") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(2.dp)
                .verticalScroll(scrollState),
        )

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier
                .padding(top = 16.dp))
        } else if (errorMessage != null) {
            Text(text = errorMessage ?: "Unknown error", color = MaterialTheme.colorScheme.error)
        } else {
            Spacer(modifier = Modifier.height(16.dp))
            val filteredStocks = stockViewModel.filteredStocks()
            filteredStocks.forEach { stock ->
                StockItem(stock = stock)
            }
        }
    }
}

@Composable
fun StockItem(stock: Stock) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)) {
        Column(modifier = Modifier
            .padding(16.dp)) {
            Text(text = stock.ticker, style = MaterialTheme.typography.bodyMedium)
            Text(text = stock.name, style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Price: $${stock.price}")
            Text(text = "Day High: $${stock.day_high}")
            Text(text = "Day Low: $${stock.day_low}")
            Text(text = "52-Week High: $${stock.week_high_52}")
            Text(text = "52-Week Low: $${stock.week_low_52}")

        }
    }
}
