package com.strattonoakmont.cryptostorage.controller

import com.strattonoakmont.cryptostorage.service.StorageService
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime

@RestController
class CryptoStorageController(val storageService: StorageService): ICryptoStorageController {
    @PostMapping(value = ["get_crypto_for_period"], consumes = [MediaType.APPLICATION_JSON_VALUE])
    override fun getAmount(@RequestBody amountRequest: AmountRequest): AmountResponse {
        return AmountResponse(storageService.getAmountForPeriod(amountRequest))
    }
}