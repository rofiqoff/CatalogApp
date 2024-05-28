package id.test.catalogapp.domain.model

data class Product(
    val id: String,
    val name: String,
    val description: String,
    val imageUrl: String,
    val isFavorite: Boolean = false,
)
