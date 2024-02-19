package org.misarch.address.event.model

/**
 * Entity for the user address created event
 */
class UserAddressDTO(
    street1: String,
    street2: String,
    city: String,
    postalCode: String,
    country: String,
    companyName: String,
    val userId: String,
) : AddressDTO(street1, street2, city, postalCode, country, companyName)