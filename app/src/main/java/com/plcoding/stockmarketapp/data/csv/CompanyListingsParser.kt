package com.plcoding.stockmarketapp.data.csv

import com.opencsv.CSVReader
import com.plcoding.stockmarketapp.domain.model.CompanyListing
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CompanyListingsParser @Inject constructor() : CSVParser<CompanyListing> {
    override suspend fun parse(stream: InputStream): List<CompanyListing> {
        return withContext(Dispatchers.IO) {
            CSVReader(InputStreamReader(stream)).use { csvReader -> // Use to ensure the reader is closed
                csvReader.readAll()
                    .drop(1) // Skip header
                    .mapNotNull { line ->
                        val symbol = line.getOrNull(0)
                        val name = line.getOrNull(1)
                        val exchange = line.getOrNull(2)

                        // Create CompanyListing only if all fields are non-null
                        if (symbol != null && name != null && exchange != null) {
                            CompanyListing(name = name, symbol = symbol, exchange = exchange)
                        } else {
                            null
                        }
                    }
            }
        }
    }
}
