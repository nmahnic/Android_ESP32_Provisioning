package com.nicomahnic.basic_esp_provisioning.login

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.nicomahnic.basic_esp_provisioning.core.BaseViewModel
import com.nicomahnic.basic_esp_provisioning.domain.repository.GetNetworks
import com.nicomahnic.basic_esp_provisioning.domain.repository.SetCredentials
import com.nicomahnic.basic_esp_provisioning.utils.DataState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class LoginVM @ViewModelInject constructor(
    private val getNetworks: GetNetworks,
    private val setCredentials: SetCredentials
) : BaseViewModel<LoginDataState, LoginViewEffect, LoginViewEvent>() {


    init {
        viewState = LoginDataState(
            state = LoginViewState.Initial
        )
    }

    override fun process(viewEvent: LoginViewEvent) {
        super.process(viewEvent)
        when (viewEvent) {
            is LoginViewEvent.ScanWiFi -> {
                viewModelScope.launch {
                    getNetworks.request()
                        .catch { exception -> Log.d("NM", "exception -> $exception") }
                        .onEach { res ->
                            when (res) {
                                is DataState.Success -> {
                                    viewEffect = LoginViewEffect.SetOK_GetNetworks(res.data.macAddress)
                                    viewState = viewState.copy(
                                        data = res.data.networks,
                                        state = LoginViewState.Scanned
                                    )
                                }
                                is DataState.Failure -> {
                                    viewEffect = LoginViewEffect.SetFAIL_GetNetworks
                                }
                            }
                        }.launchIn(viewModelScope)
                }
            }
            is LoginViewEvent.SaveCredentials -> {
                Log.d("NM","event: SaveCredentials -> SSID:${viewEvent.ssid} PASSWD:${viewEvent.passwd}")
                viewModelScope.launch {
                    setCredentials.request(viewEvent.ssid,viewEvent.passwd)
                        .catch { exception -> Log.d("NM", "exception -> $exception") }
                        .onEach { res ->
                            when (res) {
                                is DataState.Success -> {
                                    viewEffect = LoginViewEffect.SetOK_SaveCredentials(res.data.macAddress.toString())
                                }
                                is DataState.Failure -> {
                                    viewEffect = LoginViewEffect.SetFAIL_SaveCredentials
                                }
                            }
                        }.launchIn(viewModelScope)
                }
            }
        }
    }

}

 