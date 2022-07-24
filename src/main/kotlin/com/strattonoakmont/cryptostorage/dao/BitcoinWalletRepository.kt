package com.strattonoakmont.cryptostorage.dao

import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.LocalDateTime
import java.time.ZonedDateTime

@Repository
interface BitcoinWalletRepository : CrudRepository<BitcoinWallet, Long> {
    @Modifying
    @Query(
        value = "insert into crypto_schema.bitcoin_wallet(amount_total, transaction_id) values" +
                "(COALESCE((SELECT amount_total FROM crypto_schema.bitcoin_wallet ORDER BY ID DESC LIMIT 1), 0) + :amount, " +
                ":transactionId)",
        nativeQuery = true
    )
    fun insert(@Param("amount") amount: Double, @Param("transactionId") transactionId: Long)

    fun getAllByCreatedAtBetween(startDatetime: LocalDateTime, endDatetime: LocalDateTime): List<BitcoinWallet>
}