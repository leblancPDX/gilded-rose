package com.gildedrose

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.PrintStream

internal class GildedRoseTest {

    @Nested
    @DisplayName("Conjured Items")
    inner class ConjuredItemsTest {
        @Test
        fun `should lose 2 quality before sellIn`() {
            val items = listOf(
                Item("Conjured Mana Cake", 3, 6)
            )
            val app = GildedRose(items)
            assertEquals(6, items[0].quality)

            app.updateQuality()
            assertEquals(4, items[0].quality) // 6 - 2
        }

        @Test
        fun `should lose 4 quality after sellIn`() {
            val items = listOf(
                Item("Conjured Mana Cake", 0, 6)
            )
            val app = GildedRose(items)
            assertEquals(6, items[0].quality)

            app.updateQuality()
            assertEquals(2, items[0].quality) // 6 - 4
        }

        @Test
        fun `should not have negative quality`() {
            val items = listOf(
                Item("Conjured Mana Cake", 0, 1)
            )
            val app = GildedRose(items)
            assertEquals(1, items[0].quality)

            app.updateQuality()
            assertEquals(0, items[0].quality) // 2 - 4, but should not go below 0
        }
    }

    @Test
    fun `should match expected output for 100 days`() {
        val expected = File("src/test/resources/expected_output.txt").readText()
        val actual = captureOutputFromMain()
        assertEquals(expected, actual)
    }

    private fun captureOutputFromMain(): String {
        val originalOutStream = System.out
        val outputStream = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStream))
        main(arrayOf("100"))
        System.setOut(originalOutStream)

        return outputStream.toString()
    }

}
