class PlayersCircle(val players: List<Player>) {
    fun next(player: Player) =
        if (players.last() == player) players.first() else players[players.indexOf(player) + 1]
}