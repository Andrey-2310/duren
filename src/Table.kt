class Table {
    val deck = Deck()
    lateinit var circle: PlayersCircle

    //TODO:finish finding first turn
    fun selectFirstPlayer() {
        players().forEach {  }
    }

    private fun players() = circle.players
}