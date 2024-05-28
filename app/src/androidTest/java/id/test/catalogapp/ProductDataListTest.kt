package id.test.catalogapp

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import id.test.catalogapp.data.source.local.dao.ProductDao
import id.test.catalogapp.data.source.local.database.AppDatabase
import id.test.catalogapp.data.source.local.entity.ProductEntity
import id.test.catalogapp.domain.utils.StringParser
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProductDataListTest {

    private lateinit var productDao: ProductDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()

        productDao = db.productDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun givenProductList_whenInsertToDatabase_shouldSaveInDatabase() = runTest {
        val products = StringParser.resourceToList<ProductEntity>("products.json")

        productDao.insertAllProduct(products)

        val productData = productDao.getAllProducts()
        assertEquals(productData.size, products.size)
    }
}
