package org.aerovek.chartr.data.elrondsdk.usecase

import org.aerovek.chartr.data.model.elrond.esdt.EsdtConstants.ESDT_MANAGEMENT_GAS_LIMIT
import org.aerovek.chartr.data.model.elrond.esdt.EsdtConstants.ESDT_TRANSACTION_VALUE
import org.aerovek.chartr.data.model.elrond.account.Account
import org.aerovek.chartr.data.model.elrond.address.Address
import org.aerovek.chartr.data.model.elrond.esdt.EsdtConstants
import org.aerovek.chartr.data.model.elrond.network.NetworkConfig
import org.aerovek.chartr.data.model.elrond.transaction.Transaction
import org.aerovek.chartr.data.model.elrond.wallet.Wallet
import org.aerovek.chartr.data.util.toHex

@Deprecated("DO NOT USE!!! This should be converted into its respective repository implementation")
class FreezeAccountEsdtUsecase internal constructor(
    private val sendTransactionUsecase: SendTransactionUsecas