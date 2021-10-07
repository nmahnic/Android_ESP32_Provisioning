package com.nicomahnic.basic_esp_provisioning.api

interface ApiHelper {

//    suspend fun postRequest(req: CreatePaymentNetworkEntity) : CreatePaymentResponseNetworkEntity
//
//    suspend fun deleteRequest(req: DeletePaymentNetworkEntity) : CancelPaymentResponseNetworkEntity

    suspend fun getNetworksReq() : GetNetworksResponseNetworkEntity

    suspend fun setCredentialsReq(ssid: String, passwd: String): SetCredentialsResponseNetworkEntity

}