package com.elrond.erdkotlin

import com.elrond.erdkotlin.helper.TestDataProvider.transactionWithData
import com.elrond.erdkotlin.helper.TestDataProvider.wallet
import com.elrond.erdkotlin.domain.transaction.SignTransactionUsecase
import com.elrond.erdkotlin.domain.transaction.models.Transaction
import com.elrond.erdkotlin.helper.TestDataProvider
import org.junit.Assert
import org.junit.Test
import java.math.BigInteger

class SignTransactionUsecaseTest {

    @Test
    fun `sign with data field`() {
        val transaction = transactionWithData()
        val expectedSignature =
            "096c571889352947f285632d79f2b2ee1b81e7acd1