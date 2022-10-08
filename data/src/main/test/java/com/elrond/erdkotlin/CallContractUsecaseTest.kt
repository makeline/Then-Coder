package com.elrond.erdkotlin

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CallContractUsecaseTest {

    private val callContractUsecase = CallContractUsecase(sendTransactionUsecase)

    @Test
    fun `should format data correctly`() {
        val sentTransaction = callContractUsecase.execute(
            account = account,
            wallet = wallet,
            networkConfig = networkConfig,
            gasPrice = 100,
            gasLimit = 100,
            contractAddress = Address.fromBech32("erd1qqqqqqqqqqqqqpgqagvtnqn9dgnx7a6stw4n92kufathjrfd8tzqf80mkz"),
            funcName = "awesomeFunc",
            args = listOf("255", "0x5745474c442d616263646566", "0xDEADBEEF