package com.elrond.erdkotlin.management

import com.elrond.erdkotlin.domain.esdt.management.WipeAccountEsdtUsecase
import com.elrond.erdkotlin.helper.TestDataProvider.account
import com.elrond.erdkotlin.helper.TestDataProvider.networkConfig
import com.elrond.erdkotlin.helper.TestDataProvider.wallet
import com.elrond.erdkotlin.helper.TestUsecaseProvider.sendTransactionUsecase
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class WipeAccountEsdtUsecaseTest {

    private val wipeEsdtUsecase = WipeAccountEsdtUsecase(sendTransactionUsecase)

    @Test
    fun `data should be well encoded`() {
        val transaction = wipeEsdtUsecase.execute(
            account = account,
            wallet = wallet,
            networkConfig = networ