package com.sw.sample.common

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class AppUtilsTest {

    @Test
    fun `getDateFormat - valid date - returns formatted date`() {
        val dateOfBirth = "31-07-1980"
        val expectedFormattedDate = "31 Jul 1980"
        val formattedDate = AppUtils.getDateFormat(dateOfBirth)
        assertEquals(expectedFormattedDate, formattedDate)
    }

    @Test
    fun `getDateFormat - null date - returns null`() {
        val dateOfBirth: String? = null
        val formattedDate = AppUtils.getDateFormat(dateOfBirth)
        assertNull(formattedDate)
    }


    @Test
    fun `getDateFormat - different valid date 2 - returns formatted date`() {
        val dateOfBirth = "01-01-2000"
        val expectedFormattedDate = "01 Jan 2000"
        val formattedDate = AppUtils.getDateFormat(dateOfBirth)
        assertEquals(expectedFormattedDate, formattedDate)
    }
}