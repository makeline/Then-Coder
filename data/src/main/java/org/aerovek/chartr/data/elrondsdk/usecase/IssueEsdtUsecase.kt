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
        account: Account,
        wallet: Wallet,
        networkConfig: NetworkConfig,
        gasPrice: Long,
        tokenName: String,
        tokenTicker: String,
        initialSupply: BigInteger,
        numberOfDecimal: Int,
        canFreeze: Boolean? = null,
        canWipe: Boolean? = null,
        canPause: Boolean? = null,
        canMint: Boolean? = null,
        canBurn: Boolean? = null,
        canChangeOwner: Boolean? = null,
        canUpgrade: Boolean? = null,
        canAddSpecialRoles: Boolean? = null
    ) {
        execute(
            account = account,
            wallet = wallet,
            networkConfig = networkConfig,
            gasPrice = gasPrice,
            tokenName = tokenName,
            tokenTicker = tokenTicker,
            initialSupply = initialSupply,
            numberOfDecimal = numberOfDecimal,
            managementProperties = mutableMapOf<ManagementProperty, Boolean>().apply {
                if (canFreeze != null) {
                    put(ManagementProperty.CanFre