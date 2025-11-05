package com.gildedrose

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.PrintStream
import kotlin.test.Ignore

internal class GildedRoseTest {

    @Nested
    inner class ConjuredItemTest {
        @Test
        fun `should degrade by 2 if before sellIn date`() {
            val items = listOf(Item("Conjured Mana Cake", 3, 6))
            val app = GildedRose(items)
            app.updateQuality()
            assertEquals(4, items[0].quality) // 6-2
        }

        @Test
        fun `should degrade by 4 if after sellIn date`() {
            val items = listOf(Item("Conjured Mana Cake", 0, 6))
            val app = GildedRose(items)
            app.updateQuality()
            assertEquals(2, items[0].quality)
        }

        @Test
        fun `should not have negative quality`() {
            val items = listOf(Item("Conjured Mana Cake", 2, 1))
            val app = GildedRose(items)
            app.updateQuality()
            assertEquals(0, items[0].quality)
        }
    }

    @Test
    fun `should match output for 100 days`() {
        val expected = File("src/test/resources/output.txt").readText()
        val output = captureOutputFromMain()
        assertEquals(expected, output)
    }

    @Test
    @Ignore
    fun `build expected output`() {
        val output = captureOutputFromMain()
        File("src/test/resources/output.txt").writeText(output)
    }

    private fun captureOutputFromMain(): String {

        val originalOutputStream = System.out
        val outputStream = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStream))
        main(arrayOf("100"))
        System.setOut((originalOutputStream))

        return outputStream.toString()
    }

}
