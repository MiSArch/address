package org.misarch.address.persistence.repository

import com.infobip.spring.data.r2dbc.QuerydslR2dbcRepository
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.misarch.address.persistence.model.AddressEntity
import org.springframework.stereotype.Repository
import java.util.*

/**
 * Repository for [AddressEntity]s
 */
@Repository
interface AddressRepository : QuerydslR2dbcRepository<AddressEntity, UUID>


/**
 * Find the current vendor address
 *
 * @return the current vendor address or null if there is no current vendor address
 */
suspend fun AddressRepository.findCurrentVendorAddress(): AddressEntity? {
    return query {
        val entity = AddressEntity.ENTITY
        it.select(entityProjection())
            .from(entity)
            .where(entity.userId.isNull)
            .orderBy(entity.version.desc())
            .limit(1)
    }.first().awaitSingleOrNull()
}