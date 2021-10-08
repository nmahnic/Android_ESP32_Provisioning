package com.nicomahnic.basic_esp_provisioning.apis.apiServer

import com.nicomahnic.basic_esp_provisioning.apis.apiServer.networkModels.PostNewDeviceRequestNetworkEntity
import com.nicomahnic.basic_esp_provisioning.apis.apiServer.networkModels.PostNewDeviceResponseNetworkEntity
import retrofit2.http.*

interface ServerService {

    @Headers("Content-Type: application/json")
    @POST("/User")
    suspend fun postRequest(@Body req: PostNewDeviceRequestNetworkEntity): PostNewDeviceResponseNetworkEntity

}