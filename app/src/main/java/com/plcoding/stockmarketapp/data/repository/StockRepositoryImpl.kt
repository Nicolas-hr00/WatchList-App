package com.plcoding.stockmarketapp.data.repository

import com.opencsv.CSVReader
import com.plcoding.stockmarketapp.data.csv.CSVParser
import com.plcoding.stockmarketapp.data.csv.CompanyListingsParser
import com.plcoding.stockmarketapp.data.local.StockDatabase
import com.plcoding.stockmarketapp.data.mapper.toCompanyListing
import com.plcoding.stockmarketapp.data.mapper.toCompanyListingEntity
import com.plcoding.stockmarketapp.data.remote.StockApi
import com.plcoding.stockmarketapp.domain.model.CompanyListing
import com.plcoding.stockmarketapp.domain.repository.StockRepository
import com.plcoding.stockmarketapp.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.io.InputStreamReader
import javax.inject.Inject
import javax.inject.Singleton

//we should only have one singleton
@Singleton
class StockRepositoryImpl @Inject constructor( //this is the one that access the API
    private val api: StockApi,
    private val db: StockDatabase,
    private val companyListingsParser: CSVParser<CompanyListing> // we depend on the inferfase if we use a different VSC, i wont break.
): StockRepository{

    private val dao = db.dao

    override suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>> {
        return flow { //this flow throws anything we throw to the emit function
            emit(Resource.Loading(true))
            val localListings = dao.searchCompanyListing(query)
            emit(Resource.Success( //job the repository is to take the specific entities and take  the DAO an dmap the mto the domain level objects and use it for the presentation
                data = localListings.map {it.toCompanyListing()}
            ))

            val isDBEmpty = localListings.isEmpty() && query.isBlank()
            val shouldJustLoadFromCache = !isDBEmpty && !fetchFromRemote // we need to check is the DB populated already, if it is already populated we can just reload from the cash
            if (shouldJustLoadFromCache) {
                emit(Resource.Loading(false))
                return@flow
            }
            val remoteListings = try {
                val response = api.getListings()
//                response.byteStream() //this can be used to read the CSV file
                companyListingsParser.parse(response.byteStream())

            } catch(e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Counl't load data"))
                null
            } catch (e: HttpException){
                e.printStackTrace()
                emit(Resource.Error("Counl't load data"))
                null
            }


            remoteListings?.let {listings->
                emit(Resource.Success(listings))
                emit(Resource.Loading(false))
                dao.clearCompanyListings()
                dao.insertCompanyListings(
                    listings.map { it.toCompanyListingEntity() }
                )
                emit(Resource.Success(
                    data= dao
                        .searchCompanyListing("")
                        .map { it.toCompanyListing()}
                ))
                emit(Resource.Loading(false))
            }
        }
    }
}