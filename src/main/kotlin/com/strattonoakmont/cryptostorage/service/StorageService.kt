package com.strattonoakmont.cryptostorage.service

import com.strattonoakmont.cryptostorage.controller.AmountForPeriod
import com.strattonoakmont.cryptostorage.controller.AmountRequest
import com.strattonoakmont.cryptostorage.dao.BitcoinWalletRepository
import com.strattonoakmont.cryptostorage.dao.Transaction
import com.strattonoakmont.cryptostorage.dao.TransactionsRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.time.ZoneId

@Service
class StorageService(
    val transactionsRepository: TransactionsRepository,
    val bitcoinWalletRepository: BitcoinWalletRepository
) {

    private val logger = LoggerFactory.getLogger(javaClass)

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    fun saveCryptoTransfer(cryptoTransfer: CryptoTransfer) {
        logger.info("Saving transaction")
        val save = transactionsRepository.save(
            Transaction(0, "Bitcoin", cryptoTransfer.amount, cryptoTransfer.datetime)
        )
        logger.info("Transaction saved")

        logger.info("Saving to bitcoin wallet")
        bitcoinWalletRepository.insert(cryptoTransfer.amount, save.id)
        logger.info("Saved in bitcoin wallet")

    }

    fun getAmountForPeriod(amountRequest: AmountRequest): List<AmountForPeriod> {
        val bitcoinWalletList = bitcoinWalletRepository.getAllByCreatedAtBetween(
            amountRequest.startDatetime.toLocalDateTime(),
            amountRequest.endDatetime.toLocalDateTime()
        )

        val list = bitcoinWalletList.groupBy { it -> it.createdAt.hour }.map {
            val createdAt: LocalDateTime = it.value.get(0).createdAt
                .withMinute(0).withSecond(0)
            AmountForPeriod(
                createdAt.atZone(ZoneId.systemDefault()),
                it.value.maxByOrNull { it.createdAt }!!.amountTotal
            )
        }.toList()

        println(amountRequest)
        return list
    }
}