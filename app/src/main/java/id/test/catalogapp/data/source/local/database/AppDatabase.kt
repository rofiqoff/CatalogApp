package id.test.catalogapp.data.source.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import id.test.catalogapp.data.source.local.dao.ProductDao
import id.test.catalogapp.data.source.local.entity.ProductEntity

@Database(
    entities = [ProductEntity::class],
    exportSchema = true,
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao

}
