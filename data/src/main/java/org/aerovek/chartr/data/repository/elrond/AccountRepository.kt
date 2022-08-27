package org.aerovek.chartr.data.repository.elrond

import org.aerovek.chartr.data.exceptions.ElrondException
import org.aerovek.chartr.data.model.elrond.account.Account
import org.aerovek.chartr.data.model.elrond.account.AccountToken
import org.aerovek.chartr.data.model.elrond.address.Address
import java.io.IOException
import java.math.BigInteger

interface AccountRepository {

    @Throws(
        IOException::class,
        ElrondException.ProxyRequestException::class,
        ElrondException.AddressException::class
  