package com.gildedrose

class GildedRose(val items: List<Item>) {

    fun updateQuality() {
        for (i in items.indices) {
            val item = items[i]
            when {
                item.name.startsWith("Aged Brie") -> updateAgedBrie(item)
                item.name.startsWith("Backstage passes") -> updateBackStagePasses(item)
                item.name.startsWith("Sulfuras") -> updateLegendaryItem()
                else -> updateItem(item)
            }
        }
    }

    private fun updateAgedBrie(item: Item) {
        item.sellIn -= 1
        val qualityIncrease = if (item.sellIn < 0) 2 else 1
        item.quality = (item.quality + qualityIncrease).coerceAtMost(50)
    }

    private fun updateBackStagePasses(item: Item) {
        item.quality = when {
            item.sellIn <= 0 -> 0
            item.sellIn <= 5 -> item.quality + 3
            item.sellIn <= 10 -> item.quality + 2
            else -> item.quality + 1
        }.coerceAtMost(50)
        item.sellIn -= 1
    }

    private fun updateItem(item: Item) {
        item.sellIn -= 1
        item.quality = when {
            item.quality <= 0 -> 0
            item.sellIn < 0 -> item.quality - 2
            else -> item.quality - 1
        }
    }

    private fun updateLegendaryItem() {}
}
