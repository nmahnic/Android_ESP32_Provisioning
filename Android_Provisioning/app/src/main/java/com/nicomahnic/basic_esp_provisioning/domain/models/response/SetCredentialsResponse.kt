package com.nicomahnic.basic_esp_provisioning.domain.models.response

data class SetCredentialsResponse(
    override val responseCode: Int,
    override val macAddress: String
) : TransactionResponse()