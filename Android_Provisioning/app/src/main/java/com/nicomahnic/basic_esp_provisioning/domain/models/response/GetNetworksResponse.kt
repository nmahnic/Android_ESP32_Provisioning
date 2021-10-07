package com.nicomahnic.basic_esp_provisioning.domain.models.response

data class GetNetworksResponse(
    override val responseCode: Int,
    override val macAddress: String,
    val networks : List<String>?,
    val quality : List<String>?
) : TransactionResponse()