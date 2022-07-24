package com.strattonoakmont.cryptostorage.controller

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.ZonedDateTime


interface ICryptoStorageController {
    fun getAmount(amountRequest: AmountRequest): AmountResponse
}

data class AmountRequest(
    val startDatetime: ZonedDateTime,
    val endDatetime: ZonedDateTime
)

data class AmountForPeriod(val datetime: ZonedDateTime, val amount: Double)
data class AmountResponse(val amountList: List<AmountForPeriod>)
