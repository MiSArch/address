package org.misarch.address.graphql

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Query
import graphql.schema.DataFetchingEnvironment
import org.misarch.address.graphql.dataloader.AddressDataLoader
import org.misarch.address.graphql.model.Address
import org.misarch.address.graphql.model.UserAddress
import org.misarch.address.graphql.model.VendorAddress
import org.misarch.address.persistence.repository.AddressRepository
import org.misarch.address.persistence.repository.findCurrentVendorAddress
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.CompletableFuture

/**
 * Defines GraphQL queries
 *
 * @property addressRepository repository for addresses
 */
@Component
class Query(
    private val addressRepository: AddressRepository,
) : Query {

    @GraphQLDescription("Get a address by id")
    fun address(
        @GraphQLDescription("The id of the address")
        id: UUID,
        dfe: DataFetchingEnvironment
    ): CompletableFuture<Address> {
        return dfe.getDataLoader<UUID, Address>(AddressDataLoader::class.simpleName!!).load(id).thenApply {
            if (it is UserAddress) {
                val authorizedUser = dfe.authorizedUser
                if (it.userId != authorizedUser.id) {
                    authorizedUser.checkIsEmployee()
                }
            }
            it
        }
    }

    @GraphQLDescription("Get the current vendor address")
    suspend fun vendorAddress(): VendorAddress? {
        return addressRepository.findCurrentVendorAddress()?.toDTO() as VendorAddress?
    }

}