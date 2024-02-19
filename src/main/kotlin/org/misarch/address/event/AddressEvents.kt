package org.misarch.address.event

/**
 * Constants for notification event topics used in the application
 */
object AddressEvents {
    /**
     * Topic for user creation events (a user has been created)
     */
    const val USER_CREATED = "user/user/created"

    /**
     * Topic for user address creation events (a user address has been created)
     */
    const val USER_ADDRESS_CREATED = "user-address/created"

    /**
     * Topic for user address archive events (a user address has been archived)
     */
    const val USER_ADDRESS_ARCHIVED = "user-address/archived"

    /**
     * Topic for vendor address creation events (a vendor address has been created)
     */
    const val VENDOR_ADDRESS_CREATED = "vendor-address/created"

    /**
     * Name of the pubsub component
     */
    const val PUBSUB_NAME = "pubsub"
}