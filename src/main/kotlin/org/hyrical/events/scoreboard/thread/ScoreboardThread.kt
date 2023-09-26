package org.hyrical.events.scoreboard.thread

import org.hyrical.events.scoreboard.ScoreboardHandler

class ScoreboardThread : Thread() {

    override fun run() {
        while (true) {
            try {
                sleep(750)
                tick()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }

    private fun tick() {
        for (entry in ScoreboardHandler.boards) {
            val board = entry.value

            board.updateBoard()
        }
    }
}