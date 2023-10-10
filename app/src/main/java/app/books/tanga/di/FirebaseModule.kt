package app.books.tanga.di

import app.books.tanga.firestore.FirestoreOperationHandler
import app.books.tanga.firestore.FirestoreOperationHandlerImpl
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class FirebaseModule {
    @Provides
    fun provideFirebaseAuth() = Firebase.auth

    @Provides
    fun provideFirebaseFirestore() = Firebase.firestore
}

@Module
@InstallIn(SingletonComponent::class)
interface FirestoreOperationHandlerModule {
    @Binds
    fun FirestoreOperationHandlerImpl.provideFirestoreOperationHandler(): FirestoreOperationHandler
}
