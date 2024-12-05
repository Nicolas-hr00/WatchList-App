

package com.plcoding.stockmarketapp.data.csv

import android.os.Build
import androidx.annotation.RequiresApi
import com.opencsv.CSVReader
import com.plcoding.stockmarketapp.data.mapper.toIntradayInfo
import com.plcoding.stockmarketapp.data.remote.dto.IntradayInfoDto
import com.plcoding.stockmarketapp.domain.model.CompanyListing
import com.plcoding.stockmarketapp.domain.model.IntradayInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import java.time.LocalDate
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton
@Singleton
class IntradayInfoParser @Inject constructor(): CSVParser<IntradayInfo> {

    override suspend fun parse(stream: InputStream): List<IntradayInfo> {
        return withContext(Dispatchers.IO) {
            val result = mutableListOf<IntradayInfo>()
            CSVReader(InputStreamReader(stream)).use { csvReader ->
                csvReader.readNext() // Skip header
                var line = csvReader.readNext()
                while (line != null) {
                    val timestamp = line.getOrNull(0)
                    val close = line.getOrNull(4)?.toDoubleOrNull()
                    if (timestamp != null && close != null) {
                        val dto = IntradayInfoDto(timestamp, close)
                        val info = dto.toIntradayInfo()
                        if (info.date.dayOfMonth == LocalDate.now().minusDays(4).dayOfMonth) {
                            result.add(info)
                        }
                    }
                    line = csvReader.readNext()
                }
                result.sortedBy { it.date.hour }
            }
        }
    }
}