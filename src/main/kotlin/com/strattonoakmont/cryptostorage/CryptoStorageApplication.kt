package com.strattonoakmont.cryptostorage

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication
class CryptoStorageApplication

fun main(args: Array<String>) {
    runApplication<CryptoStorageApplication>(*args)
}
