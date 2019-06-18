import java.util.Scanner

class Table {

    private val cardInitAmount = 6

    val deck = Deck()
    val circle = PlayersCircle(
        listOf(
            Player(deck.getSeveralCards(cardInitAmount), "kek"),
            Player(deck.getSeveralCards(cardInitAmount), "lol")
        )
    )
    val tableCards: MutableList<Pair<Card, Card>> = ArrayList()

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
        val isDefenderCopedWithAttack = recursiveMove(attackingPlayer, circle.next(attackingPlayer))
        players()
            .filter { it.getCardsAmount() < cardInitAmount }
            //TODO: handle situation when there is no more cards in deck
            .forEach { it.takeCards(deck.getSeveralCards(cardInitAmount - it.getCardsAmount())) }
        return isDefenderCopedWithAttack
    }

    fun recursiveMove(attackingPlayer: Player, defendingPlayer: Player): Boolean {
        attackingPlayer.showCards()
        val attackingCard = attackingPlayer.receiveCardByInput()
        defendingPlayer.showCards()
        val defendingCard = defendingPlayer.cardFromGreaterOrDefault(attackingCard, deck.trump)
        if (defendingCard != null) {
            println("Your card was defended by $defendingCard")
            tableCards.add(Pair(attackingCard, defendingCard))
            recursiveMove(attackingPlayer, defendingPlayer)
        } else {
            defendingPlayer.takeCards(collectCardFromTheTable(attackingCard))
            tableCards.clear()
            return false
        }
        return true
    }

    private fun collectCardFromTheTable(attackingCard: Card) = tableCards
        .map { it.first }
        .union(tableCards.map { it.second })
        .union(setOf(attackingCard))
        .toList()

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