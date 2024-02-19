package org.misarch.address.event.model

import java.util.UUID

/**
 * DTO for archive user address events
 *
 * @property id id of the archived address
 */
data class ArchiveUserAddressDTO(
    val id: UUID
)