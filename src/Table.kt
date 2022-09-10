class Table {

    private val cardInitAmount = 6

    val deck = Deck()
    val circle = PlayersCircle(
        listOf(
            Player(deck.getSeveralCards(cardInitAmount), "kek"),
            Player(deck.getSeveralCards(cardInitAmount), "lol")
        )
    )
    private val tableCards: MutableList<Card> = ArrayList()

    init {
        players()
            .minWith(nullsLast(compareBy { it.lowestTrumpCardPriority(deck.trump) }))!!
            .attack = true
    }

    fun startGame() {
        while (deck.cards.isNotEmpty() //TODO: logic for finding loser/draw, review PlayersCircle findLoserOrDraw
        ) {
            val attackingPlayer = circle.findPlayerWithTurn()
            val result = newPlayerIteration(attackingPlayer)
            findNewAttackingPlayer(result, attackingPlayer)
        }
    }

    fun newPlayerIteration(attackingPlayer: Player): Boolean {
        val isDefenderCopedWithAttack = recursiveMove(attackingPlayer, circle.next(attackingPlayer), false)
        players()
            .filter { it.getCardsAmount() < cardInitAmount }
            //TODO: handle situation when there is no more cards in deck
            .forEach { it.takeCards(deck.getSeveralCards(cardInitAmount - it.getCardsAmount())) }
        return isDefenderCopedWithAttack
    }

    fun recursiveMove(attackingPlayer: Player, defendingPlayer: Player, abilityToRefuse: Boolean): Boolean {
        attackingPlayer.showCardsOrRefuse(abilityToRefuse)
        val attackingCard = attackingPlayer.getCardToAttack(tableCards) ?: return true
        defendingPlayer.showCardsOrRefuse(true)
        val defendingCard = defendingPlayer.getCardToDefend(attackingCard, deck.trump)
        if (defendingCard != null) {
            println("Your card was defended by $defendingCard")

            tableCards.addAll(listOf(attackingCard, defendingCard))
            println("All cards on the table: $tableCards")

            recursiveMove(attackingPlayer, defendingPlayer, true)
        } else {
            tableCards.add(attackingCard)
            defendingPlayer.takeCards(tableCards)
        }
        tableCards.clear()
        return defendingCard != null
    }

    private fun findNewAttackingPlayer(result: Boolean, previousAttackingPlayer: Player) {
        previousAttackingPlayer.attack = false
        if (result) {
            circle.next(previousAttackingPlayer)
        } else {
            circle.next(circle.next(previousAttackingPlayer))
        }.attack = true
    }

    private fun players() = circle.players
}
