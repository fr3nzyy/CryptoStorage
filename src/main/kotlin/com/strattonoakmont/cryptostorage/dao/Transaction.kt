package com.strattonoakmont.cryptostorage.dao

import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
import java.time.Month
import java.time.ZonedDateTime
import javax.persistence.*

@Entity
@Table(schema = "crypto_schema", name = "transactions")
class Transaction(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    @Column(name = "crypto_name", nullable = false)
    val cryptoName: String,
    @Column(name = "amount", nullable = false)
    val amount: Double,
    @Column(name = "transaction_time", nullable = false)
    val transactionTime: ZonedDateTime,
    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.of(1999, Month.APRIL, 1, 11, 10)
)