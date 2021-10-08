package com.nicomahnic.basic_esp_provisioning.apis.apiServer.networkModels

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.nicomahnic.basic_esp_provisioning.apis.TxnResponseNetworkEntity

data class PostNewDeviceResponseNetworkEntity(

        @SerializedName("responseCode")
        @Expose
        override val responseCode: Int,

        @SerializedName("macAddress")
        @Expose
        override val macAddress: String
) : TxnResponseNetworkEntity()