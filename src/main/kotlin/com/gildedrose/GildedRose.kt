package com.gildedrose

class GildedRose(val items: List<Item>) {

    fun updateQuality() {
        items.forEach { item ->
            val itemType = typeOf(item)
            itemType.update(item)
        }
    }
}

open class BasicItem {
    fun update(item: Item) {
        age(item)
        degrade(item)
    }

    protected open fun age(item: Item) {
        item.sellIn -= 1
    }

    protected open fun degrade(item: Item) {
        item.quality = when {
            item.sellIn < 0 -> item.quality - 2
            else -> item.quality - 1
        }
        item.quality = item.quality.coerceAtLeast(0)
    }
}

object AgedBrie : BasicItem() {
    override fun degrade(item: Item) {
        val qualityIncrease = if (item.sellIn < 0) 2 else 1
        item.quality = (item.quality + qualityIncrease).coerceAtMost(50)
    }
}

object BackstagePass : BasicItem() {
    override fun degrade(item: Item) {
        item.quality = when {
            item.sellIn < 0 -> 0
            item.sellIn < 5 -> item.quality + 3
            item.sellIn < 10 -> item.quality + 2
            else -> item.quality + 1
        }
        item.quality = item.quality.coerceAtMost(50)
    }
}

object Legendary : BasicItem() {
    override fun age(item: Item) {}
    override fun degrade(item: Item) {}
}

fun typeOf(item: Item): BasicItem {
    return when {
        item.name.startsWith("Aged Brie") -> AgedBrie
        item.name.startsWith("Backstage passes") -> BackstagePass
        item.name.startsWith("Sulfuras") -> Legendary
        else -> BasicItem()
    }
}
