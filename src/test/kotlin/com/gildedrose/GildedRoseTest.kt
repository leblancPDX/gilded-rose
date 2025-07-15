package com.gildedrose

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.PrintStream

internal class GildedRoseTest {

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
