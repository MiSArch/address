package org.misarch.address.service

import kotlinx.coroutines.reactor.awaitSingle
import org.misarch.address.event.AddressEvents
import org.misarch.address.event.EventPublisher
import org.misarch.address.graphql.input.CreateVendorAddressInput
import org.misarch.address.persistence.model.AddressEntity
import org.misarch.address.persistence.repository.AddressRepository
import org.springframework.stereotype.Service

/**
 * Service for [AddressEntity]s
 *
 * @param repository the provided repository
 * @param eventPublisher the event publisher
 */
@Service
class VendorAddressService(
    repository: AddressRepository,
    private val eventPublisher: EventPublisher
) : BaseService<AddressEntity, AddressRepository>(repository) {

    /**
     * Creates a vendor address
     *
     * @param vendorAddressInput the vendor address to create
     * @return the created vendor address
     */
    suspend fun createVendorAddress(vendorAddressInput: CreateVendorAddressInput): AddressEntity {
        val vendorAddress = AddressEntity(
            firstName = vendorAddressInput.name?.firstName,
            lastName = vendorAddressInput.name?.lastName,
            street1 = vendorAddressInput.street1,
            street2 = vendorAddressInput.street2,
            city = vendorAddressInput.city,
            postalCode = vendorAddressInput.postalCode,
            country = vendorAddressInput.country,
            companyName = vendorAddressInput.companyName,
            userId = null,
            id = null,
            version = null,
            archivedAt = null
        )
        val savedVendorAddress = repository.save(vendorAddress).awaitSingle()
        eventPublisher.publishEvent(AddressEvents.VENDOR_ADDRESS_CREATED, savedVendorAddress.toEventDTO())
        return savedVendorAddress
    }

}