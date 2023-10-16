package app.books.tanga.session

import app.books.tanga.data.favorite.FavoriteInMemoryCache
import app.books.tanga.entity.Favorite
import app.books.tanga.entity.FavoriteId
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
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
        assertEquals(favorite, cache.getBySummaryId("SummaryId1"))
    }

    @Test
    fun `removing favorite removes it from cache`() {
        val favorite = dummyFavorite1
        cache.add(favorite)
        cache.remove(favorite)
        assertNull(cache.getBySummaryId("Summary1"))
    }

    @Test
    fun `getBySummaryId retrieves correct favorite`() {
        val favorite1 = dummyFavorite1
        val favorite2 = dummyFavorite2
        cache.add(favorite1)
        cache.add(favorite2)
        assertEquals(favorite2, cache.getBySummaryId("SummaryId2"))
    }

    @Test
    fun `getAll retrieves all favorites`() {
        val favorite1 = dummyFavorite1
        val favorite2 = dummyFavorite2
        cache.add(favorite1)
        cache.add(favorite2)
        assertEquals(listOf(favorite1, favorite2), cache.getAll())
    }

    @Test
    fun `putAll adds multiple favorites to cache`() {
        val favorites = listOf(
            dummyFavorite1,
            dummyFavorite2
        )
        cache.putAll(favorites)
        assertEquals(favorites, cache.getAll())
    }

    @Test
    fun `isEmpty returns true when cache is empty`() {
        assertTrue(cache.isEmpty())
    }

    @Test
    fun `isEmpty returns false when cache is not empty`() {
        val favorite = dummyFavorite1
        cache.add(favorite)
        assertFalse(cache.isEmpty())
    }

    @Test
    fun `clear removes all entries from cache`() {
        val favorite = dummyFavorite1
        cache.add(favorite)
        cache.clear()
        assertTrue(cache.isEmpty())
    }

    companion object {
        val dummyFavorite1 = Favorite(
            id = FavoriteId("1"),
            title = "Summary1",
            author = "Author1",
            coverUrl = "CoverUrl1",
            userId = "UserId1",
            summaryId = "SummaryId1",
            playingLength = "PlayingLength1"
        )

        val dummyFavorite2 = Favorite(
            id = FavoriteId("2"),
            title = "Summary2",
            author = "Author2",
            coverUrl = "CoverUrl2",
            userId = "UserId2",
            summaryId = "SummaryId2",
            playingLength = "PlayingLength2"
        )
    }
}
