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
                    put(ManagementProperty.CanFreeze, canFreeze)
                }
                if (canWipe != null) {
                    put(ManagementProperty.CanWipe, canWipe)
                }
                if (canPause != null) {
                    put(ManagementProperty.CanPause, canPause)
                }
                if (canMint != null) {
                    put(ManagementProperty.CanMint, canMint)
                }
                if (canBurn != null) {
                    put(ManagementProperty.CanBurn, canBurn)
                }
                if (canChangeOwner != null) {
                    put(ManagementProperty.CanChangeOwner, canChangeOwner)
                }
                if (canUpgrade != null) {
                    put(ManagementProperty.CanUpgrade, canUpgrade)
                }
                if (canAddSpecialRoles != null) {
                    put(ManagementProperty.CanAddSpecialRoles, canAddSpecialRoles)
                }
            }
        )
    }

    fun execute(
        account: Account,
        wallet: Wallet,
        networkConfig: NetworkConfig,
        gasPrice: Long,
        tokenName: String,
        tokenTicker: String,
        initialSupply: BigInteger,
        numberOfDecimal: Int,
        managementProperties: Map<ManagementProperty, Boolean> = emptyMap()
    ): Transaction {
        if (!tokenName.matches("^[A-Za-z0-9]{3,20}$".toRegex())) {
            throw IllegalArgumentException(
                "tokenName length should be between 3 and 20 characters " +
      