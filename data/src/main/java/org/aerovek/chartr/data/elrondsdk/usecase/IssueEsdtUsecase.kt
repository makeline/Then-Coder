package org.aerovek.chartr.data.elrondsdk.usecase

import org.aerovek.chartr.data.elrondsdk.model.*
import org.aerovek.chartr.data.model.elrond.account.Account
import org.aerovek.chartr.data.model.elrond.esdt.EsdtConstants
import org.aerovek.chartr.data.model.elrond.network.NetworkConfig
import org.aerovek.chartr.data.model.elrond.transaction.Transaction
import org.aerovek.chartr.data.model.elrond.wallet.Wallet
import org.aerovek.chartr.data.util.ScUtils
import org.aerovek.chartr.data.util.toHex
import org.aerovek.chartr.data.util.toHexString
import java.math.BigInteger

@Deprecated("DO NOT USE!!! This should be converted into its respective repository implementation")
class IssueEsdtUsecase internal constructor(
    private val sendTransactionUsecase: SendTransactionUsecase
) {

    fun execute(
        account: Accou