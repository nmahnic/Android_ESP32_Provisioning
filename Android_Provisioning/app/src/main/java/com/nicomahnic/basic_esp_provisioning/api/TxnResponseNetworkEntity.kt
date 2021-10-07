package com.nicomahnic.basic_esp_provisioning.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

abstract class TxnResponseNetworkEntity {
        abstract val responseCode: Int
        abstract val macAddress: String
}

data class GetNetworksResponseNetworkEntity(

        @SerializedName("responseCode")
        @Expose
        override val responseCode: Int,

        @SerializedName("macAddress")
        @Expose
        override val macAddress: String,

        @SerializedName("data")
        @Expose
        val data: List<String>?
) : TxnResponseNetworkEntity()

data class SetCredentialsResponseNetworkEntity(

        @SerializedName("responseCode")
        @Expose
        override val responseCode: Int,

        @SerializedName("macAddress")
        @Expose
        override val macAddress: String
) : TxnResponseNetworkEntity()