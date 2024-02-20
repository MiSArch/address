package org.misarch.address.graphql.model.connection

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.federation.directives.ShareableDirective
import com.querydsl.core.types.Expression
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.ComparableExpression
import com.querydsl.sql.SQLQuery
import org.misarch.address.graphql.AuthorizedUser
import org.misarch.address.graphql.model.Address
import org.misarch.address.graphql.model.UserAddress
import org.misarch.address.graphql.model.connection.base.*
import org.misarch.address.persistence.model.AddressEntity
import org.misarch.address.persistence.repository.AddressRepository

/**
 * A GraphQL connection for [UserAddress]s.
 *
 * @param first The maximum number of items to return
 * @param skip The number of items to skip
 * @param filter The filter to apply to the items
 * @param predicate The predicate to filter the items by
 * @param order The order to sort the items by
 * @param repository The repository to fetch the items from
 * @param authorizedUser The authorized user
 * @param applyJoin A function to apply a join to the query
 */
@GraphQLDescription("A connection to a list of `Address` values.")
@ShareableDirective
class UserAddressConnection(
    first: Int?,
    skip: Int?,
    filter: UserAddressFilter?,
    predicate: BooleanExpression?,
    order: UserAddressOrder?,
    repository: AddressRepository,
    authorizedUser: AuthorizedUser?,
    applyJoin: (query: SQLQuery<*>) -> SQLQuery<*> = { it }
) : BaseConnection<Address, AddressEntity>(
    first,
    skip,
    filter,
    predicate,
    (order ?: UserAddressOrder.DEFAULT).toOrderSpecifier(UserAddressOrderField.ID),
    repository,
    AddressEntity.ENTITY,
    authorizedUser,
    applyJoin
) {

    override val primaryKey: ComparableExpression<*> get() = AddressEntity.ENTITY.id

    @GraphQLDescription("The resulting items.")
    override suspend fun nodes(): List<UserAddress> {
        return super.nodes().map { it as UserAddress }
    }

}

@GraphQLDescription("User address order fields")
enum class UserAddressOrderField(override vararg val expressions: Expression<out Comparable<*>>) : BaseOrderField {
    @GraphQLDescription("Order addresss by their id")
    ID(AddressEntity.ENTITY.id),


}

@GraphQLDescription("User address order")
class UserAddressOrder(
    direction: OrderDirection?, field: UserAddressOrderField?
) : BaseOrder<UserAddressOrderField>(direction, field) {

    companion object {
        val DEFAULT = UserAddressOrder(OrderDirection.ASC, UserAddressOrderField.ID)
    }
}

@GraphQLDescription("User address filter")
class UserAddressFilter(
    val isArchived: Boolean?
) : BaseFilter {

    override fun toExpression(): BooleanExpression? {
        return if (isArchived != null) {
            AddressEntity.ENTITY.archivedAt.let {
                if (isArchived) it.isNotNull else it.isNull
            }
        } else {
            null
        }
    }

}