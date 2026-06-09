package com.mrp.sml

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SmokeTest {

    @Test
    fun packageName_exists() {
        assertTrue("Package name should be com.mrp.sml",
            "com.mrp.sml" == "com.mrp.sml")
    }
}
