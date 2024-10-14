package com.example.stock

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf

class StockViewModel : ViewModel() {

    var stockList = mutableStateOf<List<Stock>>(emptyList())
    var isLoading = mutableStateOf(true)
    var errorMessage = mutableStateOf<String?>(null)
    var searchText = mutableStateOf("")

    init {
        getStocks()
    }

    private fun getStocks() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getStockPrices()
                if (response.isSuccessful && response.body() != null) {
                    stockList.value = response.body()!!.data.map {
                        Stock(
                            ticker = it.ticker,
                            name = it.name,
                            price = it.price,
                            day_high = it.day_high,
                            day_low = it.day_low,
                            day_open = it.day_open,
                            week_high_52 = it.week_high_52,
                            week_low_52 = it.week_low_52
                        )
                    }
                    isLoading.value = false
                } else {
                    errorMessage.value = "Failed to fetch stock prices"
                }
            } catch (e: Exception) {
                errorMessage.value = e.message
            }
        }
    }


    fun filteredStocks(): List<Stock> {
        val query = searchText.value.lowercase()
        return stockList.value.filter {
            it.ticker.lowercase().contains(query) || it.name.lowercase().contains(query)
        }
    }
}
