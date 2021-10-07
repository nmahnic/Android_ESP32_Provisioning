package com.nicomahnic.basic_esp_provisioning.domain.models.request

data class CreateSaleIntentRequest (
        override val androidId: String,
        override val regId: String,
        val amount: Double,
        val orderId: String
) : TransactionRequest()