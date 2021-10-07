package com.nicomahnic.basic_esp_provisioning.domain.models.response

abstract class TransactionResponse {
    abstract val responseCode: Int
    abstract val macAddress: String
}

