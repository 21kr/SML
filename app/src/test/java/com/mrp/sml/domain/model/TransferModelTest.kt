package com.mrp.sml.domain.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class TransferModelTest {

    @Test
    fun createTransferModel_defaultValues() {
        val model = TransferModel(
            id = "test-id",
            fileName = "test.txt",
            fileSize = 1024L,
            direction = TransferModel.TransferDirection.SENT,
            status = TransferModel.TransferStatus.PENDING
        )
        assertEquals("test-id", model.id)
        assertEquals("test.txt", model.fileName)
        assertEquals(1024L, model.fileSize)
        assertEquals(TransferModel.TransferDirection.SENT, model.direction)
        assertEquals(TransferModel.TransferStatus.PENDING, model.status)
        assertEquals(0f, model.progress, 0.001f)
        assertTrue(model.startedAt > 0)
    }

    @Test
    fun transferStatus_enumValues() {
        val values = TransferModel.TransferStatus.values()
        assertTrue(values.contains(TransferModel.TransferStatus.PENDING))
        assertTrue(values.contains(TransferModel.TransferStatus.TRANSFERRING))
        assertTrue(values.contains(TransferModel.TransferStatus.PAUSED))
        assertTrue(values.contains(TransferModel.TransferStatus.COMPLETED))
        assertTrue(values.contains(TransferModel.TransferStatus.FAILED))
        assertTrue(values.contains(TransferModel.TransferStatus.CANCELLED))
    }

    @Test
    fun transferDirection_enumValues() {
        assertEquals(2, TransferModel.TransferDirection.values().size)
        assertEquals(TransferModel.TransferDirection.SENT, TransferModel.TransferDirection.valueOf("SENT"))
        assertEquals(TransferModel.TransferDirection.RECEIVED, TransferModel.TransferDirection.valueOf("RECEIVED"))
    }

    @Test
    fun completedAt_setOnCompletion() {
        val now = System.currentTimeMillis()
        val model = TransferModel(
            id = "id", fileName = "f", fileSize = 1L,
            direction = TransferModel.TransferDirection.SENT,
            status = TransferModel.TransferStatus.COMPLETED,
            completedAt = now
        )
        assertNotNull(model.completedAt)
        assertEquals(now, model.completedAt)
    }
}
