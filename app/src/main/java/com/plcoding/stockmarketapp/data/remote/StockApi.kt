package com.plcoding.stockmarketapp.data.remote

import com.plcoding.stockmarketapp.data.remote.dto.CompanyInfo
import com.plcoding.stockmarketapp.data.remote.dto.CompanyInfoDto
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface StockApi {

    @GET("query?function=LISTING_STATUS")
    suspend fun getListings( //gets the listing data
        @Query("apikey") apiKey: String = API_KEY
    ): ResponseBody //Gets acess o a file stream. This is how we wan to interact with the data (INSERT, DELETE)

    @GET ("query?function=TIME_SERIES_INTRADAY&interval=60min&datatype=csv")
    suspend fun getIntradayInfo(
        @Query ("symbol" ) symbol: String,
        @Query ("apikey") apikey: String = API_KEY
    ): ResponseBody

    @GET ("query?function=OVERVIEW") //GETTING JSON DATA FOR COMPANY INFO
    suspend fun getCompanyInfo (
        @Query ("symbol") symbol: String,
        @Query ("apikey") apikey: String = API_KEY
    ): CompanyInfoDto
    companion object {
        const val API_KEY = "HXGSACO7X3ULS5DX"
        const val BASE_URL = "https://alphavantage.co"
    }
}