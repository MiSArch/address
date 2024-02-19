package org.misarch.address.graphql.model

import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.generator.federation.directives.FieldSet
import com.expediagroup.graphql.generator.federation.directives.KeyDirective
import java.util.*

@GraphQLDescription("An address associated with the vendor.")
@KeyDirective(fields = FieldSet("id"))
class VendorAddress(
    id: UUID,
    street1: String,
    street2: String,
    city: String,
    postalCode: String,
    country: String,
    companyName: String
) : Address(id, street1, street2, city, postalCode, country, companyName)