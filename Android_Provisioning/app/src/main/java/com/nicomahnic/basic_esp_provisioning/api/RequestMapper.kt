package com.nicomahnic.basic_esp_provisioning.api

import com.nicomahnic.basic_esp_provisioning.utils.EntityMapper
import com.nicomahnic.basic_esp_provisioning.domain.models.request.StatusRequest
import javax.inject.Inject

class StatusRequestMapper @Inject constructor():
    EntityMapper<StatusPaymentNetworkEntity, StatusRequest> {

    override fun mapFromEntity(entity: StatusPaymentNetworkEntity): StatusRequest {
        return StatusRequest(
            androidId = entity.androidID,
            regId = entity.regId,
            extRefId = entity.extRefId.toString()
        )
    }

    override fun mapToEntity(domainModel: StatusRequest): StatusPaymentNetworkEntity {
        return StatusPaymentNetworkEntity(
            androidID = domainModel.androidId,
            regId = domainModel.regId,
            extRefId = domainModel.extRefId.toInt()
        )
    }

}

//class CreateRequestMapper @Inject constructor():
//        EntityMapper<CreatePaymentNetworkEntity, CreateSaleIntentRequest> {
//
//    override fun mapFromEntity(entity: CreatePaymentNetworkEntity): CreateSaleIntentRequest {
//        return CreateSaleIntentRequest(
//            androidId = entity.androidID,
//            orderId = entity.orderId,
//            amount = entity.amount,
//            regId = entity.regId
//        )
//    }
//
//    override fun mapToEntity(domainModel: CreateSaleIntentRequest): CreatePaymentNetworkEntity {
//        return CreatePaymentNetworkEntity(
//            androidID = domainModel.androidId,
//            orderId = domainModel.orderId,
//            amount = domainModel.amount,
//            regId = domainModel.regId
//        )
//    }
//
//}
//
//class CancelRequestMapper @Inject constructor():
//        EntityMapper<DeletePaymentNetworkEntity, CancelSaleIntentRequest> {
//
//    override fun mapFromEntity(entity: DeletePaymentNetworkEntity): CancelSaleIntentRequest {
//        return CancelSaleIntentRequest(
//            androidId = entity.androidID,
//            regId = entity.regId,
//            extRefId = entity.extRefId.toString()
//        )
//    }
//
//    override fun mapToEntity(domainModel: CancelSaleIntentRequest): DeletePaymentNetworkEntity {
//        return DeletePaymentNetworkEntity(
//            androidID = domainModel.androidId,
//            regId = domainModel.regId,
//            extRefId = domainModel.extRefId.toInt()
//        )
//    }
//
//}