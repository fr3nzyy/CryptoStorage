package com.strattonoakmont.cryptostorage.dao

import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
import java.time.Month
import javax.persistence.*

@Entity
@Table(schema = "crypto_schema", name = "bitcoin_wallet")
class BitcoinWallet (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @Column(name = "amount_total", nullable = false)
    val amountTotal: Double,
    @Column(name = "transaction_id", nullable = false)
    val transactionId: Long,
    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.of(1999, Month.APRIL, 1, 11, 10)
    )