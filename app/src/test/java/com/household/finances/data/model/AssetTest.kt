package com.household.finances.data.model

import org.junit.Assert.*
import org.junit.Test

class AssetTest {

    @Test
    fun `Asset creation with all fields`() {
        val asset = Asset(
            id = 1,
            name = "测试房产",
            type = AssetType.REAL_ESTATE,
            value = 1000000.0,
            purchasePrice = 800000.0,
            purchaseDate = System.currentTimeMillis(),
            description = "测试描述",
            category = "房产",
            tags = listOf("投资", "房产")
        )

        assertEquals(1L, asset.id)
        assertEquals("测试房产", asset.name)
        assertEquals(AssetType.REAL_ESTATE, asset.type)
        assertEquals(1000000.0, asset.value, 0.001)
        assertEquals(800000.0, asset.purchasePrice, 0.001)
        assertEquals("测试描述", asset.description)
        assertEquals("房产", asset.category)
        assertEquals(2, asset.tags.size)
    }

    @Test
    fun `Asset creation with default values`() {
        val asset = Asset(
            name = "现金",
            type = AssetType.CASH,
            value = 50000.0
        )

        assertEquals(0L, asset.id)
        assertEquals("现金", asset.name)
        assertEquals(AssetType.CASH, asset.type)
        assertEquals(50000.0, asset.value, 0.001)
        assertEquals(0.0, asset.purchasePrice, 0.001)
        assertEquals("", asset.description)
        assertEquals("", asset.category)
        assertTrue(asset.tags.isEmpty())
    }

    @Test
    fun `AssetType enum values`() {
        assertEquals(5, AssetType.entries.size)
        assertNotNull(AssetType.REAL_ESTATE)
        assertNotNull(AssetType.CASH)
        assertNotNull(AssetType.INVESTMENT)
        assertNotNull(AssetType.INSURANCE)
        assertNotNull(AssetType.OTHER)
    }

    @Test
    fun `Asset copy with modified value`() {
        val original = Asset(
            id = 1,
            name = "测试",
            type = AssetType.CASH,
            value = 10000.0
        )
        val modified = original.copy(value = 20000.0)

        assertEquals(1L, modified.id)
        assertEquals("测试", modified.name)
        assertEquals(20000.0, modified.value, 0.001)
    }
}
