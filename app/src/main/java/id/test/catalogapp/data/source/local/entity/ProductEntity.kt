package id.test.catalogapp.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import id.test.catalogapp.domain.model.Product

@Entity(tableName = "product")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo("product_id") val productId: String = "",
    @ColumnInfo("name") val name: String,
    @ColumnInfo("description") val description: String,
    @ColumnInfo("image_url") val imageUrl: String,
    @ColumnInfo("is_favorite") val isFavorite: Boolean = false,
) {

    val asProduct
        get() = Product(
            id = productId,
            name = name,
            description = description,
            imageUrl = imageUrl,
            isFavorite = isFavorite
        )
}

fun List<ProductEntity>.asProductList(): List<Product> {
    return this.map { it.asProduct }
}
