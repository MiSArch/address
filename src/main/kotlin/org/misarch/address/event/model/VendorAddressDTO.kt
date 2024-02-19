package org.misarch.address.event.model

import java.util.*

/**
 * Entity for the vendor address created event
 *
 * @param id id of the address
 * @param street1 first line of the address
 * @param street2 second line of the address
 * @param city city of the address
 * @param postalCode postal code of the address
 * @param country country of the address
 * @param companyName company name of the address
 */
class VendorAddressDTO(
    id: UUID,
    street1: String,
    street2: String,
    city: String,
    postalCode: String,
    country: String,
    companyName: String
) : AddressDTO(id, street1, street2, city, postalCode, country, companyName)