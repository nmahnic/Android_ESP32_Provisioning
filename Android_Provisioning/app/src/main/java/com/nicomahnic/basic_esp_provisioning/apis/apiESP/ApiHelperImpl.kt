package com.nicomahnic.basic_esp_provisioning.apis.apiESP

import com.nicomahnic.basic_esp_provisioning.apis.apiESP.networkModels.GetNetworksResponseNetworkEntity
import com.nicomahnic.basic_esp_provisioning.apis.apiESP.networkModels.SetCredentialsResponseNetworkEntity
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(
    private val apiService: ApiService
): ApiHelper {

    override suspend fun getNetworksReq() : GetNetworksResponseNetworkEntity = apiService.getNetworksReq()

    override suspend fun setCredentialsReq(ssid: String, passwd: String) : SetCredentialsResponseNetworkEntity = apiService.setCredentialsReq(ssid, passwd)

}