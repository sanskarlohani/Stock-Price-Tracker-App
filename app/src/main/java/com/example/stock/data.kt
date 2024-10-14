package com.example.stock

data class Stock(
    val ticker: String,
    val name: String,
    val price: Double,
    val day_high: Double,
    val day_low: Double,
    val day_open: Double,
    val week_high_52: Double,
    val week_low_52: Double

)

data class StockResponse(
    val data: List<Stock>
)
