package org.misarch.address.persistence.model

import org.misarch.address.event.model.AddressDTO
import org.misarch.address.event.model.NameDTO
import org.misarch.address.event.model.UserAddressDTO
import org.misarch.address.event.model.VendorAddressDTO
import org.misarch.address.graphql.model.Address
import org.misarch.address.graphql.model.Name
import org.misarch.address.graphql.model.UserAddress
import org.misarch.address.graphql.model.VendorAddress
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.OffsetDateTime
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
    val firstName: String?,
    val lastName: String?,
    val street1: String,
    val street2: String,
    val city: String,
    val postalCode: String,
    val country: String,
    val companyName: String?,
    val userId: UUID?,
    val version: Long?,
    var archivedAt: OffsetDateTime?,
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
        val name = if (firstName != null && lastName != null) {
            Name(
                firstName = firstName,
                lastName = lastName
            )
        } else {
            null
        }
        return if (userId != null) {
            UserAddress(
                id = id!!,
                name = name,
                street1 = street1,
                street2 = street2,
                city = city,
                postalCode = postalCode,
                country = country,
                companyName = companyName,
                userId = userId,
                archivedAt = archivedAt
            )
        } else {
            VendorAddress(
                id = id!!,
                name = name,
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
        val name = if (firstName != null && lastName != null) {
            NameDTO(
                firstName = firstName,
                lastName = lastName
            )
        } else {
            null
        }
        return if (userId != null) {
            UserAddressDTO(
                id = id!!,
                name = name,
                street1 = street1,
                street2 = street2,
                city = city,
                postalCode = postalCode,
                country = country,
                companyName = companyName,
                userId = userId
            )
        } else {
            VendorAddressDTO(
                id = id!!,
                name = name,
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