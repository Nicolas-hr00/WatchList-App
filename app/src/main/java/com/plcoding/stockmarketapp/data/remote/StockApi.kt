package com.plcoding.stockmarketapp.data.remote

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface StockApi {

    @GET("query?function=LISTINGS_STATUS")
    suspend fun getListings( //gets the listing data
        @Query("apikey") apiKey: String = API_KEY
    ): ResponseBody //Gets acess o a file stream. This is how wewanto interact with the data (INSERT, DELETE)
    companion object {
        const val API_KEY = "HXGSACO7X3ULS5DX "
        const val BASE_URL = "https://alphavantage.co"
    }
}