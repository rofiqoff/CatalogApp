package id.test.catalogapp.data.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.test.catalogapp.data.repositories.ProductRepositoryImpl
import id.test.catalogapp.data.source.local.dao.ProductDao
import id.test.catalogapp.domain.repositories.ProductRepository

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    fun provideProductRepository(productDao: ProductDao): ProductRepository {
        return ProductRepositoryImpl(productDao)
    }

}
