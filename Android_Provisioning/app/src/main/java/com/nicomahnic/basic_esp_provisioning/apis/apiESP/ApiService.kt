package com.nicomahnic.basic_esp_provisioning.apis.apiESP

import com.nicomahnic.basic_esp_provisioning.apis.apiESP.networkModels.GetNetworksResponseNetworkEntity
import com.nicomahnic.basic_esp_provisioning.apis.apiESP.networkModels.SetCredentialsResponseNetworkEntity
import retrofit2.http.*

interface ApiService {

    @GET("/wifi")
    suspend fun getNetworksReq(): GetNetworksResponseNetworkEntity

    @GET("/wifisave?")
    suspend fun setCredentialsReq(
            @Query("s") ssid : String,
            @Query("p") passwd: String,
    ): SetCredentialsResponseNetworkEntity

}