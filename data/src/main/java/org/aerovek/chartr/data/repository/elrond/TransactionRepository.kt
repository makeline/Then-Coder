package org.aerovek.chartr.data.repository.elrond

import org.aerovek.chartr.data.exceptions.ElrondException
import org.aerovek.chartr.data.model.elrond.address.Address
import org.aerovek.chartr.data.model.elrond.transaction.SimulateTransactionInfo
import org.aerovek.chartr.data.model.elrond.transaction.Transaction
import org.aerovek.chartr.data.model.elrond.transaction.TransactionInfo
import org.aerovek.chartr.data.model.elrond.transaction.TransactionOnNetwork
import org.aerovek.chartr.data.model.elrond.wallet.Wallet
import java.io.IOException

interface TransactionRepository {
    @Throws(
        IOException::class,
        ElrondException.CannotSerializeTransactionException::class,
        ElrondException.ProxyRequestException::class
    )
    fun sendTransaction(transaction: Transaction, wallet: Wallet): Transaction

    @Throws(
        IOException::class,
        ElrondException.CannotSerializeTransactionException::class,
        ElrondException