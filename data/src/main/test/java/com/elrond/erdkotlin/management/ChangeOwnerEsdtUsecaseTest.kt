package com.elrond.erdkotlin.management

import com.elrond.erdkotlin.domain.esdt.management.ChangeOwnerEsdtUsecase
import com.elrond.erdkotlin.helper.TestDataProvider.account
import com.elrond.erdkotlin.helper.TestDataProvider.networkConfig
import com.elrond.erdkotlin.helper.TestDataProvider.wallet
import com.elrond.erdkotlin.helper.TestUsecaseProvider.sendTransactionUsecase
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ChangeOwnerEsdtUsecaseTest {

    private val changeOwnerEsdtUsecase = ChangeOwnerEsdtUsecase(sendTransactionUsecase)

    @Test
    fun `data should be well encoded`() {
        val transaction = changeOwnerEsdtUsecase.execute(
            account = account,
            wallet = wallet,
            networkConfig = networkConfig,
            gasPrice = networkConfig.minGasPrice,
            tokenIdentifier = "ERDKT6972-b6ed2a",
            newOwnerAddress = Address.fromBech32("erd17te5zg2pnxtsmnpuppkupeuhmeul0txtj8y5guh0fytxed0m4tzqazsj9z"),
        )

        assertEquals(
            "transferOwnership