package com.nicomahnic.basic_esp_provisioning.login

import com.nicomahnic.basic_esp_provisioning.domain.models.response.TransactionResponse
import java.lang.Exception

// STATE
data class LoginDataState(
    val exception: Exception? = null,
    val data: List<String>? = null,
    val state: LoginViewState
)

//VIEW EFFECT
sealed class LoginViewEffect {
    data class SetOK_GetNetworks(val macAddress: String): LoginViewEffect()
    object SetFAIL_GetNetworks: LoginViewEffect()
    data class SetOK_SaveCredentials(val macAddress: String): LoginViewEffect()
    object SetFAIL_SaveCredentials: LoginViewEffect()
}

// VIEW EVENT
sealed class LoginViewEvent {
    object ScanWiFi: LoginViewEvent()
    data class SaveCredentials(val ssid: String, val passwd: String): LoginViewEvent()
    data class SetNewDevice(val dato: String): LoginViewEvent()
}

// VIEW STATE
sealed class LoginViewState {
    object Initial: LoginViewState()
    object Scanned: LoginViewState()
    object Connected: LoginViewState()
}