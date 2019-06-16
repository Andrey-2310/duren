class PlayersCircle(val players: List<Player>) {
    fun next(player: Player) =
        if (players.last() == player) players.first() else players[players.indexOf(player) + 1]

    fun findPlayerWithTurn() = players.first { it.attack }

    fun findLoserOrDraw(): String =
        when (players.filter { it.cardsInHand.isNotEmpty() }.size) {
            0 -> "Draw"
            1 -> "${players.first { it.cardsInHand.isNotEmpty() }.name} is Loser"
            else -> ""
        }
}