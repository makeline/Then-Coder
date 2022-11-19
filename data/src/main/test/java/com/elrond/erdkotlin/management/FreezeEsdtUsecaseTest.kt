package com.elrond.erdkotlin.management

import com.elrond.erdkotlin.domain.esdt.management.FreezeAccountEsdtUsecase
import com.elrond.erdkotlin.helper.TestDataProvider.account
import com.elrond.erdkotlin.helper.TestDataProvider.networkConfig
import com.elrond.erdkotlin.helper.TestDataProvider.wallet
import com.elrond.erdkotlin.helper.TestUsecaseProvider.sendTransactionUsecase
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FreezeEsdtUsecaseTest {

    private val freezeEsdtUsecase = FreezeAccountEsdtUsecase(sendTransactionUsecase)

    @Test
    fun `data should be well encoded freeze`() {
        val transaction = freezeEsdtUsecase.execute(
            account = account,
            wallet = wallet,
            netw