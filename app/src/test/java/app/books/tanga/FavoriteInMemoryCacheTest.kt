package app.books.tanga

import app.books.tanga.data.favorite.FavoriteInMemoryCache
import app.books.tanga.fixtures.Fixtures.dummyFavorite1
import app.books.tanga.fixtures.Fixtures.dummyFavorite2
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class FavoriteInMemoryCacheTest {

    private lateinit var cache: FavoriteInMemoryCache

    @BeforeEach
    fun setUp() {
        cache = FavoriteInMemoryCache()
    }

    @Test
    fun `adding favorite puts it in cache`() {
        val favorite = dummyFavorite1
        cache.add(favorite)
        Assertions.assertEquals(favorite, cache.getBySummaryId("SummaryId1"))
    }

    @Test
    fun `removing favorite removes it from cache`() {
        val favorite = dummyFavorite1
        cache.add(favorite)
        cache.remove(favorite)
        Assertions.assertNull(cache.getBySummaryId("Summary1"))
    }

    @Test
    fun `getBySummaryId retrieves correct favorite`() {
        val favorite1 = dummyFavorite1
        val favorite2 = dummyFavorite2
        cache.add(favorite1)
        cache.add(favorite2)
        Assertions.assertEquals(favorite2, cache.getBySummaryId("SummaryId2"))
    }

    @Test
    fun `getAll retrieves all favorites`() {
        val favorite1 = dummyFavorite1
        val favorite2 = dummyFavorite2
        cache.add(favorite1)
        cache.add(favorite2)
        Assertions.assertEquals(listOf(favorite1, favorite2), cache.getAll())
    }

    @Test
    fun `putAll adds multiple favorites to cache`() {
        val favorites = listOf(
            dummyFavorite1,
            dummyFavorite2
        )
        cache.putAll(favorites)
        Assertions.assertEquals(favorites, cache.getAll())
    }

    @Test
    fun `isEmpty returns true when cache is empty`() {
        Assertions.assertTrue(cache.isEmpty())
    }

    @Test
    fun `isEmpty returns false when cache is not empty`() {
        val favorite = dummyFavorite1
        cache.add(favorite)
        Assertions.assertFalse(cache.isEmpty())
    }

    @Test
    fun `clear removes all entries from cache`() {
        val favorite = dummyFavorite1
        cache.add(favorite)
        cache.clear()
        Assertions.assertTrue(cache.isEmpty())
    }
}
