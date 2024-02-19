package org.misarch.address.graphql.dataloader

import org.misarch.address.graphql.model.Address
import org.misarch.address.graphql.model.UserAddress
import org.misarch.address.persistence.model.AddressEntity
import org.misarch.address.persistence.repository.AddressRepository
import org.springframework.stereotype.Component

/**
 * Data loader for [UserAddress]s
 *
 * @param repository repository for [AddressEntity]s
 */
@Component
class AddressDataLoader(
    repository: AddressRepository
) : IdDataLoader<Address, AddressEntity>(repository)