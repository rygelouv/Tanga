package app.books.tanga.feature.profile

import app.books.tanga.data.user.UserRepository
import app.books.tanga.entity.User
import app.books.tanga.utils.resultOf
import javax.inject.Inject

class ProfileInteractor @Inject constructor(
    private val userRepository: UserRepository
) {

    suspend fun getUserInfo(): Result<User?> {
        return resultOf {
            val user = userRepository.getUser().getOrThrow()
            user
        }
    }
}