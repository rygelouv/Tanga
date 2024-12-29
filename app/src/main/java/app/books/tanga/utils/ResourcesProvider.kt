package app.books.tanga.utils

import android.content.Context
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

/**
 * Wrapper class that helps getting android resources without depending on the context
 * This class will help get resources without having to worry if it comes from ContextCompat,
 * ResourcesCompat, AnimationUtils, or Context.resources
 */
interface ResourcesProvider {

    @ColorInt
    fun getColor(@ColorRes resourceId: Int): Int

    fun getString(@StringRes resourceId: Int): String

    fun getString(@StringRes resourceId: Int, vararg formatArgs: Any): String

    fun getPermissionStatus(permission: String): Int
}

class ResourcesProviderImpl(private val context: Context) : ResourcesProvider {

    override fun getColor(@ColorRes resourceId: Int): Int = context.getColor(resourceId)

    override fun getString(@StringRes resourceId: Int): String = context.getString(resourceId)

    override fun getString(@StringRes resourceId: Int, vararg formatArgs: Any): String = context.getString(
        resourceId,
        *formatArgs
    )

    override fun getPermissionStatus(permission: String): Int = ContextCompat.checkSelfPermission(context, permission)
}

@Module
@InstallIn(SingletonComponent::class)
class ResourcesProviderModule {

    @Provides
    fun provideResourcesProvider(
        @ApplicationContext context: Context
    ): ResourcesProvider = ResourcesProviderImpl(context)
}
