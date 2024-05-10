package org.misarch.address.event.model

import java.util.*

/**
 * Entity for the user address created event
 * 
 * @param id id of the address
 * @param name name of the address
 * @param street1 first line of the address
 * @param street2 second line of the address
 * @param city city of the address
 * @param postalCode postal code of the address
 * @param country country of the address
 * @param companyName company name of the address
 * @property userId id of the user the address belongs to
 */
class UserAddressDTO(
    id: UUID,
    name: NameDTO?,
    street1: String,
    street2: String,
    city: String,
    postalCode: String,
    country: String,
    companyName: String?,
    val userId: UUID,
) : AddressDTO(id, name, street1, street2, city, postalCode, country, companyName)