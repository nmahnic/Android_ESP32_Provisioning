package com.nicomahnic.basic_esp_provisioning.apis

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

abstract class TxnResponseNetworkEntity {
        abstract val responseCode: Int
        abstract val macAddress: String
}