/*
The MIT License (MIT)

Copyright (c) 2023-present Aerovek

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
package org.aerovek.chartr.ui.wallet.transaction

import android.app.Application
import android.content.SharedPreferences
import android.widget.CompoundButton
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.hadilq.liveevent.LiveEvent
import kotlinx.coroutines.launch
import org.aerovek.chartr.data.buildconfig.EnvironmentRepository
import org.aerovek.chartr.data.cache.WalletCache
import org.aerovek.chartr.data.model.elrond.address.Address
import org.aerovek.chartr.data.network.ElrondNetwork
import org.aerovek.chartr.data.repository.elrond.AccountRepository
import org.aerovek.chartr.data.repository.elrond.ElrondNetworkRepository
import org.aerovek.chartr.data.repository.elrond.EsdtRepository
import org.aerovek.chartr.ui.AppConstants
import org.aerovek.chartr.ui.BaseViewModel
import org.aerovek.chartr.util.*
import java.lang.Exception
import java.net.SocketTimeoutException

class SendAeroViewModel(
    app: Application,
    networkRepository: ElrondNetworkRepository,
    private val dispatcherProvider: DispatcherProvider,
    esdtRepository: EsdtRepository,
    private val accountRepository: AccountRepository,
    sharedPreferences: SharedPreferences,
    private val environmentRepository: EnvironmentRepository
    ) : BaseViewModel(app) {

    private val _navigationEvent = LiveEvent<NavigationEvent>()
    val navigationEvent: LiveData<NavigationEvent> = _navigationEvent

    val recipientAddressText = MutableLiveData("")
    val amountText = MutableLiveData("")
    val usdAmountText = MutableLiveData("")
    val showLoadingView = MutableLiveData(true)
    val showInvalidAddress = LiveEvent<Unit>()
    val scannerTapped = LiveEvent<Unit>()
    val sendAeroChecked = MutableLiveData(true)
    val sendEgldChecked = MutableLiveData(false)

    private var aeroBalanceText: String? = null
    var aeroPrice = 0.0

    private var egldBalanceText: String? = null
    var egldPrice = 0.0

    init {
        viewModelScope.launch(dispatcherProvider.IO) {
            try {

   