package com.strattonoakmont.cryptostorage.service

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.ZonedDateTime

data class CryptoTransfer(
    @JsonProperty("datetime") val datetime: ZonedDateTime,
    @JsonProperty("amount") var amount: Double
)