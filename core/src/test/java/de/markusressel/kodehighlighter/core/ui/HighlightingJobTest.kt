package de.markusressel.kodehighlighter.core.ui

import androidx.compose.ui.text.AnnotatedString
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class HighlightingJobTest {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    private val expected = AnnotatedString("a")

    @After
    fun tearDown() {
        scope.cancel()
    }

    @Test
    fun `result that is ready within the budget is returned`() {
        val job = HighlightingJob(scope) { expected }
        assertEquals(expected, job.awaitWithin(5_000))
    }

    @Test
    fun `slow result gives null and is still delivered later`() {
        val gate = CompletableDeferred<Unit>()
        val job = HighlightingJob(scope) {
            gate.await()
            expected
        }
        assertNull(job.awaitWithin(1))

        gate.complete(Unit)
        assertEquals(expected, runBlocking { job.await() })
    }

    @Test
    fun `work that ignores cancellation does not extend the budget`() {
        val release = CountDownLatch(1)
        val job = HighlightingJob(scope) {
            release.await(5, TimeUnit.SECONDS)
            expected
        }
        assertNull(job.awaitWithin(1))
        release.countDown()
    }
}
