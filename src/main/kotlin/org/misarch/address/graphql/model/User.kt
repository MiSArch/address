package org.misarch.address.graphql.model

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.annotations.GraphQLIgnore
import com.expediagroup.graphql.generator.federation.directives.FieldSet
import com.expediagroup.graphql.generator.federation.directives.KeyDirective
import graphql.schema.DataFetchingEnvironment
import org.misarch.address.graphql.authorizedUser
import org.misarch.address.graphql.authorizedUserOrNull
import org.misarch.address.graphql.model.connection.UserAddressConnection
import org.misarch.address.graphql.model.connection.UserAddressFilter
import org.misarch.address.graphql.model.connection.UserAddressOrder
import org.misarch.address.persistence.model.AddressEntity
import org.misarch.address.persistence.repository.AddressRepository
import org.springframework.beans.factory.annotation.Autowired
import java.util.*

@GraphQLDescription("A user.")
@KeyDirective(fields = FieldSet("id"))
class User(
    id: UUID
) : Node(id) {

    @GraphQLDescription("Get all address the user received")
    fun address(
        @GraphQLDescription("Number of items to return")
        first: Int? = null,
        @GraphQLDescription("Number of items to skip")
        skip: Int? = null,
        @GraphQLDescription("Ordering")
        orderBy: UserAddressOrder? = null,
        @GraphQLDescription("Filtering")
        filter: UserAddressFilter? = null,
        @GraphQLIgnore
        @Autowired
        productVariantRepository: AddressRepository,
        dfe: DataFetchingEnvironment
    ): UserAddressConnection {
        if (dfe.authorizedUser.id != id) {
            dfe.authorizedUser.checkIsEmployee()
        }
        return UserAddressConnection(
            first, skip, filter, AddressEntity.ENTITY.userId.eq(id), orderBy, productVariantRepository, dfe.authorizedUserOrNull
        )
    }

}