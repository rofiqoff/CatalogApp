package id.test.catalogapp.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.test.catalogapp.data.source.local.entity.ProductEntity

@Dao
interface ProductDao {

    @Query("SELECT * FROM product")
    suspend fun getAllProducts(): List<ProductEntity>

    @Query("SELECT * FROM product WHERE name LIKE '%'||:keyword||'%' OR description LIKE '%'||:keyword||'%'")
    suspend fun findProductByKeyword(keyword: String): List<ProductEntity>

    @Query("SELECT * FROM product WHERE id = :id LIMIT 1")
    suspend fun getProduct(id: String): ProductEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllProduct(products: List<ProductEntity>)

    @Query("UPDATE product SET is_favorite = :isFavorite WHERE product_id = :productId")
    suspend fun updateIsFavoriteProduct(productId: String, isFavorite: Boolean)

    @Query("DELETE FROM product")
    suspend fun clearProductData()

}
