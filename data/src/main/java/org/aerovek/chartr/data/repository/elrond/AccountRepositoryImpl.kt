package org.aerovek.chartr.data.repository.elrond

import org.aerovek.chartr.data.model.elrond.account.Account
import org.aerovek.chartr.data.model.elrond.address.Address
import org.aerovek.chartr.data.model.elrond.toDomain
import org.aerovek.chartr.data.exceptions.ElrondException
import org.aerovek.chartr.data.model.elrond.account.AccountToken
import org.aerovek.chartr.data.network.ElrondApiService
import org.aerovek.chartr.data.network.ElrondGatewayService
import java.io.IOException
import java.math.BigInteger

internal class AccountRepositoryImpl(
    private val elrondGatewayService: ElrondGatewayService,
    private val elrondApi