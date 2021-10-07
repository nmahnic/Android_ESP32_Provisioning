package com.nicomahnic.basic_esp_provisioning.api

import com.nicomahnic.basic_esp_provisioning.utils.EntityMapper
import com.nicomahnic.basic_esp_provisioning.domain.models.response.GetNetworksResponse
import com.nicomahnic.basic_esp_provisioning.domain.models.response.SetCredentialsResponse
import javax.inject.Inject


class GetNetworksResponseMapper @Inject constructor():
    EntityMapper<GetNetworksResponseNetworkEntity, GetNetworksResponse> {

    override fun mapFromEntity(entity: GetNetworksResponseNetworkEntity): GetNetworksResponse {
        return GetNetworksResponse(
                data = entity.data,
                responseCode = entity.responseCode,
                macAddress = entity.macAddress
        )
    }

    override fun mapToEntity(domainModel: GetNetworksResponse): GetNetworksResponseNetworkEntity {
        return GetNetworksResponseNetworkEntity(
                data = domainModel.data,
                responseCode = domainModel.responseCode,
                macAddress = domainModel.macAddress
        )
    }

}

class SetCredentialsResponseMapper @Inject constructor():
    EntityMapper<SetCredentialsResponseNetworkEntity, SetCredentialsResponse> {

    override fun mapFromEntity(entity: SetCredentialsResponseNetworkEntity): SetCredentialsResponse {
        return SetCredentialsResponse(
            responseCode = entity.responseCode,
            macAddress = entity.macAddress
        )
    }

    override fun mapToEntity(domainModel: SetCredentialsResponse): SetCredentialsResponseNetworkEntity {
        return SetCredentialsResponseNetworkEntity(
            responseCode = domainModel.responseCode,
            macAddress = domainModel.macAddress
        )
    }

}
