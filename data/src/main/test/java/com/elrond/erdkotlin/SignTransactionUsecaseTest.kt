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
            "096c571889352947f285632d79f2b2ee1b81e7acd19ee20510d34002eba0f999b4720f50211b039dd40914284f84c14eb84815bb045c14dbed036f2e87431307"
        val expectedJson =
            "{'nonce':7,'value':'10000000000000000000','receiver':'erd1cux02zersde0l7hhklzhywcxk4u9n4py5tdxyx7vrvhnza2r4gmq4vw35r','sender':'erd1l453hd0gt5gzdp7czpuall8ggt2dcv5zwmfdf3sd3lguxseux2fsmsgldz','gasPrice':1000000000,'gasLimit':70000,'data':'Zm9yIHRoZSBib29rIHdpdGggc3Rha2U=','chainID':'1','version':1,'signature':'$expectedSignature'}".replace(
