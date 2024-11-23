package com.plcoding.stockmarketapp.domain.repository

import com.plcoding.stockmarketapp.data.remote.dto.CompanyInfo
import com.plcoding.stockmarketapp.domain.model.CompanyListing
import com.plcoding.stockmarketapp.domain.model.IntradayInfo
import com.plcoding.stockmarketapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {

    suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query:String
    ): Flow<Resource<List<CompanyListing>>>

    suspend fun getIntradayInfo (
        Symbol: String

    ): Resource <List <IntradayInfo>>

    suspend fun getCompanyInfo(
        Symbol: String
    ): Resource <CompanyInfo>
}
