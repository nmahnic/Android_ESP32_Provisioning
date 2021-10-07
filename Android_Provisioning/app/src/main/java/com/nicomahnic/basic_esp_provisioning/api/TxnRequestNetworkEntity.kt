package com.nicomahnic.basic_esp_provisioning.api

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

abstract class TxnRequestNetworkEntity {
        abstract val androidID: String
        abstract val regId: String
}

//data class CreatePaymentNetworkEntity (
//
//        @SerializedName("AndroidID")
//        @Expose
//        override val androidID: String,
//
//        @SerializedName("RegistrationId")
//        @Expose
//        override val regId: String,
//
//        @SerializedName("SaleId")
//        @Expose
//        val orderId: String,
//
//        @SerializedName("Amount")
//        @Expose
//        val amount: Double
//
//) : TxnRequestNetworkEntity()
//
//data class DeletePaymentNetworkEntity (
//
//        @SerializedName("AndroidID")
//        @Expose
//        override val androidID: String,
//
//        @SerializedName("RegistrationId")
//        @Expose
//        override val regId: String,
//
//        @SerializedName("ExternalReferenceId")
//        @Expose
//        val extRefId: Int
//
//) : TxnRequestNetworkEntity()

data class StatusPaymentNetworkEntity (

        @SerializedName("AndroidID")
        @Expose
        override val androidID: String,

        @SerializedName("RegistrationId")
        @Expose
        override val regId: String,

        @SerializedName("ExternalReferenceId")
        @Expose
        val extRefId: Int

) : TxnRequestNetworkEntity()