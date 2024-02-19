package org.misarch.address.service

import org.misarch.address.event.model.UserDTO
import org.misarch.address.persistence.model.UserEntity
import org.misarch.address.persistence.repository.UserRepository
import org.springframework.stereotype.Service

/**
 * Service for [UserEntity]s
 *
 * @param repository the repository for [UserEntity]s
 */
@Service
class UserService(
    repository: UserRepository
) : BaseService<UserEntity, UserRepository>(repository) {

    /**
     * Registers a user
     *
     * @param userDTO the user to register
     */
    suspend fun registerUser(userDTO: UserDTO) {
        repository.createUser(userDTO.id)
    }

}