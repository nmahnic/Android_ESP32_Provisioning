package com.nicomahnic.basic_esp_provisioning.apis.apiServer

import com.nicomahnic.basic_esp_provisioning.apis.apiServer.networkModels.PostNewDeviceRequestNetworkEntity
import com.nicomahnic.basic_esp_provisioning.apis.apiServer.networkModels.PostNewDeviceResponseNetworkEntity


interface ServerHelper {

    suspend fun postRequest(req: PostNewDeviceRequestNetworkEntity) : PostNewDeviceResponseNetworkEntity

}