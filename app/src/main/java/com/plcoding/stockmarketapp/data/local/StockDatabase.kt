package com.plcoding.stockmarketapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database (
    entities = [CompanyListingEntity::class],
    version = 1 //we only have 1 database, if we add more we can increase
)

abstract class StockDatabase : RoomDatabase(){ //it takes this information for the database to create the DAO
    abstract val dao: StockDao
}