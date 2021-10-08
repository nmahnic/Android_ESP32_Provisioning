package com.nicomahnic.basic_esp_provisioning.apis.apiESP

import com.nicomahnic.basic_esp_provisioning.apis.apiServer.ServerHelper
import com.nicomahnic.basic_esp_provisioning.apis.apiServer.ServerService
import com.nicomahnic.basic_esp_provisioning.apis.apiServer.networkModels.PostNewDeviceRequestNetworkEntity
import com.nicomahnic.basic_esp_provisioning.apis.apiServer.networkModels.PostNewDeviceResponseNetworkEntity
import javax.inject.Inject

class ServerHelperImpl @Inject constructor(
    private val apiService: ServerService
): ServerHelper {

    override suspend fun postRequest(req: PostNewDeviceRequestNetworkEntity) : PostNewDeviceResponseNetworkEntity = apiService.postRequest(req)

}