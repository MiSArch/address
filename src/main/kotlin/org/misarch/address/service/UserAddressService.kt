package org.misarch.address.service

import kotlinx.coroutines.reactor.awaitSingle
import org.misarch.address.event.AddressEvents
import org.misarch.address.event.EventPublisher
import org.misarch.address.event.model.ArchiveUserAddressDTO
import org.misarch.address.graphql.AuthorizedUser
import org.misarch.address.graphql.input.ArchiveUserAddressInput
import org.misarch.address.graphql.input.CreateUserAddressInput
import org.misarch.address.persistence.model.AddressEntity
import org.misarch.address.persistence.repository.AddressRepository
import org.misarch.address.persistence.repository.UserRepository
import org.springframework.stereotype.Service
import java.time.OffsetDateTime

/**
 * Service for [AddressEntity]s
 *
 * @param repository the provided repository
 * @param userRepository the user repository
 * @param eventPublisher the event publisher
 */
@Service
class UserAddressService(
    repository: AddressRepository,
    private val userRepository: UserRepository,
    private val eventPublisher: EventPublisher
) : BaseService<AddressEntity, AddressRepository>(repository) {

    /**
     * Creates a user address
     *
     * @param userAddressInput the user address to create
     * @return the created user address
     */
    suspend fun createUserAddress(userAddressInput: CreateUserAddressInput): AddressEntity {
        if (!userRepository.existsById(userAddressInput.userId).awaitSingle()) {
            throw IllegalArgumentException("User with id ${userAddressInput.userId} does not exist")
        }
        val userAddress = AddressEntity(
            firstName = userAddressInput.name?.firstName,
            lastName = userAddressInput.name?.lastName,
            street1 = userAddressInput.street1,
            street2 = userAddressInput.street2,
            city = userAddressInput.city,
            postalCode = userAddressInput.postalCode,
            country = userAddressInput.country,
            companyName = userAddressInput.companyName,
            userId = userAddressInput.userId,
            id = null,
            version = null,
            archivedAt = null
        )
        val savedUserAddress = repository.save(userAddress).awaitSingle()
        eventPublisher.publishEvent(AddressEvents.USER_ADDRESS_CREATED, savedUserAddress.toEventDTO())
        return savedUserAddress
    }

    /**
     * Archives a user address
     * Also checks permissions
     *
     * @param archiveUserAddressInput defines the user address to archive
     * @return the archived user address
     */
    suspend fun archiveUserAddress(
        archiveUserAddressInput: ArchiveUserAddressInput, authorizedUser: AuthorizedUser
    ): AddressEntity {
        val userAddress = repository.findById(archiveUserAddressInput.id).awaitSingle()
        if (userAddress.userId != authorizedUser.id) {
            authorizedUser.checkIsEmployee()
        }
        userAddress.archivedAt = OffsetDateTime.now()
        val savedUserAddress = repository.save(userAddress).awaitSingle()
        eventPublisher.publishEvent(
            AddressEvents.USER_ADDRESS_ARCHIVED, ArchiveUserAddressDTO(userAddress.id!!, userAddress.userId!!)
        )
        return savedUserAddress
    }

}