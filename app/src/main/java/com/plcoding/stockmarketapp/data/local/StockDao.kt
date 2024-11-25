package com.plcoding.stockmarketapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StockDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE) //if we insert a company listing with and ID already exists, it will replace it
    suspend fun insertCompanyListings(
        companyListingEntities: List<CompanyListingEntity>
    )

    @Query("DELETE FROM companylistingentity")
    suspend fun clearCompanyListings() //clear everything on the data base

    @Query(
        """
            SELECT * 
            FROM companylistingentity
            WHERE LOWER(name) LIKE '%' || LOWER(:query) || '%' OR 
                UPPER(:query) == symbol
        """
    )//taking the name of the company and comparing it the search query to lower case, and check if the name contains a word that matches
    suspend fun searchCompanyListing(query: String): List<CompanyListingEntity>//search something on the database
}