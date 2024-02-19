package org.misarch.address.event.model

/**
 * Entity for the vendor address created event
 */
class VendorAddressDTO(
    street1: String,
    street2: String,
    city: String,
    postalCode: String,
    country: String,
    companyName: String
) : AddressDTO(street1, street2, city, postalCode, country, companyName)