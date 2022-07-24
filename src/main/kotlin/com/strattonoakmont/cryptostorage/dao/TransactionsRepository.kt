package com.strattonoakmont.cryptostorage.dao

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TransactionsRepository: CrudRepository<Transaction, Long>{
}