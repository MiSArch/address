package org.misarch.address.persistence.model

import org.misarch.address.event.model.AddressDTO
import org.misarch.address.event.model.UserAddressDTO
import org.misarch.address.event.model.VendorAddressDTO
import org.misarch.address.graphql.model.Address
import org.misarch.address.graphql.model.UserAddress
import org.misarch.address.graphql.model.VendorAddress
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.util.*

/**
 * Entity for addresses (both user and vendor)
 *
 * @property street1 first line of the address
 * @property street2 second line of the address
 * @property city city of the address
 * @property postalCode postal code of the address
 * @property country country of the address
 * @property companyName company name of the address
 * @property id unique identifier of the address
 */
@Table
class AddressEntity(
    val street1: String,
    val street2: String,
    val city: String,
    val postalCode: String,
    val country: String,
    val companyName: String,
    val userId: UUID?,
    val version: Long?,
    var isArchived: Boolean,
    @Id
    override val id: UUID?
) : BaseEntity<Address> {

    companion object {
        /**
         * Querydsl entity
         */
        val ENTITY = QAddressEntity.addressEntity!!
    }

    override fun toDTO(): Address {
        return if (userId != null) {
            UserAddress(
                id = id!!,
                street1 = street1,
                street2 = street2,
                city = city,
                postalCode = postalCode,
                country = country,
                companyName = companyName,
                userId = userId,
                isArchived = isArchived
            )
        } else {
            VendorAddress(
                id = id!!,
                street1 = street1,
                street2 = street2,
                city = city,
                postalCode = postalCode,
                country = country,
                companyName = companyName
            )
        }
    }

    /**
     * Converts the entity to an event DTO
     *
     * @return the event DTO
     */
    fun toEventDTO(): AddressDTO {
        return if (userId != null) {
            UserAddressDTO(
                street1 = street1,
                street2 = street2,
                city = city,
                postalCode = postalCode,
                country = country,
                companyName = companyName,
                userId = userId.toString()
            )
        } else {
            VendorAddressDTO(
                street1 = street1,
                street2 = street2,
                city = city,
                postalCode = postalCode,
                country = country,
                companyName = companyName
            )
        }
    }

}