package com.nicomahnic.basic_esp_provisioning.domain.models.request

abstract class TransactionRequest {
    abstract val androidId: String
    abstract val regId: String
}