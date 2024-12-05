
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
class CompanyListingsParser @Inject constructor(): CSVParser<CompanyListing> {


    override suspend fun parse(stream: InputStream): List<CompanyListing> {
        return withContext(Dispatchers.IO) {
            CSVReader(InputStreamReader(stream)).use { csvReader ->
                val result = mutableListOf<CompanyListing>()
                csvReader.readNext() // Skip the header
                var line = csvReader.readNext()
                while (line != null) {
                    val symbol = line.getOrNull(0)
                    val name = line.getOrNull(1)
                    val exchange = line.getOrNull(2)
                    if (symbol != null && name != null && exchange != null) {
                        result.add(
                            CompanyListing(
                                name = name,
                                symbol = symbol,
                                exchange = exchange
                            )
                        )
                    }
                    line = csvReader.readNext()
                }
                result
            }
        }
    }

//    override suspend fun parse(stream: InputStream): List<CompanyListing> {
//        val csvReader = CSVReader(InputStreamReader(stream))
//        val lines = withContext(Dispatchers.IO) {
//            csvReader.readAll()
//        }
//        return lines
//                .drop(1)
//                .mapNotNull { line ->
//                    val symbol = line.getOrNull(0)
//                    val name = line.getOrNull(1)
//                    val exchange = line.getOrNull(2)
//                    CompanyListing(
//                        name = name ?: return@mapNotNull null,
//                        symbol = symbol ?: return@mapNotNull null,
//                        exchange = exchange ?: return@mapNotNull null
//                    )
//                }
//                .also {
//                    csvReader.close()
//                }
//        }
    }
