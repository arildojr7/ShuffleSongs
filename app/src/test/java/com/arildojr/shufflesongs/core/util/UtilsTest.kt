package com.arildojr.shufflesongs.core.util

import org.junit.Test

import org.junit.Assert.*

class UtilsTest {

    @Test
    fun `Combine list, when it is requests, then combine list alternated`() {
        // ARRANGE
        val list1 = listOf("1","2","3","4","5")
        val list2 = listOf("a","b","c","d","e")
        val list3 = listOf("!","@","#","$","%")
        val sd = arrayOf(list1, list2, list3)
        val expectedResult = listOf("1", "a", "!", "2", "b", "@", "3", "c", "#", "4", "d", "$", "5", "e", "%")

        // ACT
        val combineResult = combine(sd)

        // ASSERT
        assertEquals(expectedResult, combineResult)
    }
}