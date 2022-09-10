import java.util.Scanner

class Player(
    val cardsInHand: MutableList<Card>,
    val name: String,
    var attack: Boolean = false
) {

    val scanner = Scanner(System.`in`)

    fun showCards() = cardsInHand.forEachIndexed { index, card -> println("${index + 1} - $card") }

    /**
     * Refuse means Done for the attacking player
     * and Take for defending player
     */
    fun showCardsOrRefuse(abilityToRefuse: Boolean) {
        if (abilityToRefuse) {
            println("0 - ${if (attack) "Done" else "Take"}")
        }
        showCards()
    }

    fun getCardToAttack(tableCards: List<Card>) =
        if (tableCards.isEmpty()) {
            receiveCard(true)
        } else {
            receiveCard() { card -> tableCards.map { it.speech }.contains(card.speech) }
        }

    fun getCardToDefend(attackingCard: Card, trump: Suit): Card? {
        println("Attacking card: $attackingCard")
        val greaterCards = greaterCards(attackingCard, trump)
        return if (greaterCards.isEmpty()) {
            println("There are no cards to defend")
            null
        } else cardFromGreaterOrNull(greaterCards)
    }

    private fun cardFromGreaterOrNull(greaterCards: List<Card>) =
        receiveCard() { greaterCards.contains(it) }

    private fun receiveCard(isTableCardEmpty: Boolean = false, validate: (Card) -> Boolean = { true }): Card? {
        while (true) {
            println("${name}: Select card:")
            val index = scanner.nextInt()

            if (isTableCardEmpty.not() && index == 0) return null
            if (index < getMinCardIndex(isTableCardEmpty)
                || index > cardsInHand.size
                || cardsInHand[index - 1].takeIf(validate) == null
            ) {
                println("Card you selected is not available, try another one")
                continue
            }
            return cardsInHand.removeAt(index - 1)
        }
    }

    fun similarCards(tableCards: List<Card>) =
        cardsInHand.filter { tableCards.map { it.speech }.contains(it.speech) }

    fun greaterCards(attackingCard: Card, trump: Suit) = cardsInHand.filter { it.isGreaterThan(attackingCard, trump) }

    fun lowestTrumpCardPriority(trump: Suit) = cardsInHand
        .filter { it.suit == trump || (it.jokerSuit != null && it.jokerSuit.overheadedSuits.contains(trump)) }
        .minOfOrNull { it.speech.priority }

    fun takeCards(cards: List<Card>) = cardsInHand.addAll(cards)

    fun getCardsAmount() = cardsInHand.size

    // If there are no cards on the table then Done/Take option is not available
    private fun getMinCardIndex(isTableCardEmpty: Boolean) = if (isTableCardEmpty) 1 else 0
}
