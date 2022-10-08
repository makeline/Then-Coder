package com.elrond.erdkotlin

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CallContractUsecaseTest {

    private val callContractUsecase = CallContractUsecase(sendTransactionUsecase)

  