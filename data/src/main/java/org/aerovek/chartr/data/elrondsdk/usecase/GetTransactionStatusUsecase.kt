
package org.aerovek.chartr.data.elrondsdk.usecase

import org.aerovek.chartr.data.model.elrond.address.Address
import org.aerovek.chartr.data.repository.elrond.TransactionRepository

@Deprecated("DO NOT USE!!! This should be converted into its respective repository implementation")
class GetTransactionStatusUsecase internal constructor(private val transactionRepository: TransactionRepository) {

    fun execute(txHash: String, sender: Address? = null) =
        transactionRepository.getTransactionStatus(txHash, sender)
}