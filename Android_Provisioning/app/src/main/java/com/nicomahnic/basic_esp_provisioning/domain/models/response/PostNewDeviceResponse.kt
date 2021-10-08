package com.nicomahnic.basic_esp_provisioning.domain.models.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PostNewDeviceResponse(
    val responseCode: Int,
    val macAddress: String
)
