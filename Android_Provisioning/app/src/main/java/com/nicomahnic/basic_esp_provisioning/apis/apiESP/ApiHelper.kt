package com.nicomahnic.basic_esp_provisioning.apis.apiESP

import com.nicomahnic.basic_esp_provisioning.apis.apiESP.networkModels.GetNetworksResponseNetworkEntity
import com.nicomahnic.basic_esp_provisioning.apis.apiESP.networkModels.SetCredentialsResponseNetworkEntity

interface ApiHelper {

    suspend fun getNetworksReq() : GetNetworksResponseNetworkEntity

    suspend fun setCredentialsReq(ssid: String, passwd: String): SetCredentialsResponseNetworkEntity

}