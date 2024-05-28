package id.test.catalogapp.data.source.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.test.catalogapp.data.source.local.entity.FavoriteProductEntity

@Dao
interface FavoriteProductDao {

    @Query("SELECT * FROM favorite_product")
    suspend fun getAllFavoriteProducts(): List<FavoriteProductEntity>

    @Query("SELECT * FROM favorite_product WHERE product_id = :productId")
    suspend fun getFavoriteProductByProductId(productId: String): FavoriteProductEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToFavorite(product: FavoriteProductEntity)

    @Query("DELETE FROM favorite_product WHERE product_id = :productId")
    suspend fun deleteFavorite(productId: String)

}
