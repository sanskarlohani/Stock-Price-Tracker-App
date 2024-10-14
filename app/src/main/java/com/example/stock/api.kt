package com.example.stock

import retrofit2.Response
import retrofit2.http.GET

interface StockApiService {
    @GET("v1/data/quote?symbols=AAPL%2CTSLA%2CMSFT&api_token=YhoP8Qa82BiNxV0CfY6GyYNWfHMovPpsbWp8TGf4")
    suspend fun getStockPrices(): Response<StockResponse>
}
