package org.hyrical.events.scoreboard

import org.bukkit.entity.Player
import org.hyrical.events.utils.translate
import org.hyrical.fastboard.FastBoard

class EventsScoreboard(
    val player: Player,
    var board: FastBoard = FastBoard(
        player
    )
) {

    fun updateBoard(){
        val title = ScoreboardHandler.adapter.getTitle(player)
        val boardLines: List<String> = ScoreboardHandler.adapter.getLines(player).map { translate(it) }.toList()

        if (boardLines.isEmpty() && !board.isDeleted){
            board.delete()
            return
        }

        if (board.isDeleted){
            board = FastBoard(player)
        }

        board.updateTitle(translate(title))
        board.updateLines(boardLines)
    }

}