package com.nicomahnic.basic_esp_provisioning.domain.models.request

data class CancelSaleIntentRequest (
        override val androidId: String,
        override val regId: String,
        val extRefId: String
) : TransactionRequest()