package com.example.racetracker

import com.example.racetracker.ui.RaceParticipant
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class RaceParticipantTest {
    private val raceParticipant = RaceParticipant(
        name = "Test",
        maxProgress = 100,
        progressDelayMillis = 500L,
        initialProgress = 0,
        progressIncrement = 1
    )

    @Test
    fun raceParticipant_RaceStarted_ProgressUpdated() = runTest {
        val expectedProgress = 1
        launch { raceParticipant.run() }
        /**
         * Note: You can directly call the raceParticipant.run() in the runtTest builder, but the
         * default test implementation ignores the call to delay(). As a result, the run() finishes
         * executing before you can analyze the progress.

         * The value of the raceParticipant.progressDelayMillis property determines the duration
         * after which the race progress updates. In order to test the progress after
         * progressDelayMillis time has elapsed, you need to add some form of delay to your test.
         */
        advanceTimeBy(raceParticipant.progressDelayMillis)

        /**
         * Since advanceTimeBy() doesn't run the task scheduled at the given duration, you need to
         * call the runCurrent() function. This function executes any pending tasks at the current
         * time.
         */
        runCurrent()

        assertEquals(expectedProgress, raceParticipant.currentProgress)
    }

    @Test
    fun raceParticipant_RaceFinished_ProgressUpdated() = runTest {
        launch { raceParticipant.run() }

        advanceTimeBy(raceParticipant.maxProgress * raceParticipant.progressDelayMillis)

        runCurrent()

        assertEquals(100, raceParticipant.currentProgress)
    }

}
