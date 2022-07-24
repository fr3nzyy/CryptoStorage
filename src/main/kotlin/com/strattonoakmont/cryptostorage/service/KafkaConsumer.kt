package com.strattonoakmont.cryptostorage.service

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component

@Component
class KafkaConsumer(
    val storageService: StorageService
) {
    private val logger = LoggerFactory.getLogger(javaClass)

    @KafkaListener(topics = ["\${kafka.topics.crypto}"], groupId = "ppr")
    fun consume(consumerRecord: ConsumerRecord<Any, Any>, ack: Acknowledgment) {
        logger.info("Message received {}", consumerRecord)
        val cryptoTransfer: CryptoTransfer = consumerRecord.value() as CryptoTransfer
        try {
            storageService.saveCryptoTransfer(cryptoTransfer)
        } catch (ex: Exception) {
            logger.error(
                "Got exception while saving transaction: amount {}, datetime {}",
                cryptoTransfer.amount, cryptoTransfer.datetime
            )
            ack.nack(1000)
        }
        ack.acknowledge()
    }

}