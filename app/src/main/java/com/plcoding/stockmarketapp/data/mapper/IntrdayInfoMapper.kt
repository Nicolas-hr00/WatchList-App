package com.plcoding.stockmarketapp.data.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.plcoding.stockmarketapp.data.remote.dto.IntradayInfoDto
import com.plcoding.stockmarketapp.domain.model.IntradayInfo
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
fun IntradayInfoDto.toIntradayInfo(): IntradayInfo {
    val patter = "yyyy-MM-dd HH:mm:ss"
    val formatter = DateTimeFormatter.ofPattern(patter, Locale.getDefault())
    val localDateTime = LocalDateTime.parse(timestamp)
    return  IntradayInfo(
        date = localDateTime,
        close = close
    )
}