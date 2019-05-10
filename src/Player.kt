class Player(val cardsInHand: MutableList<Card>, val turn: Boolean = false) {

    fun showCards() = cardsInHand.forEachIndexed { index, card -> println("${index + 1} - $card") }

    fun getCard(index: Int): Card {
        return try {
            cardsInHand[index - 1]
        } catch (exception: IndexOutOfBoundsException) {
            println("Entered index is out of bounds, first card selected")
            cardsInHand[0]
        }
    }

    fun getSimilarCards(tableCards: List<Card>) =
        cardsInHand.filter { tableCards.map { it.speech }.contains(it.speech) }

    fun getGreaterCards(card: Card, trump: Suit) = cardsInHand.filter { it.isGreaterThan(card, trump) }

    fun getLowestTrumpCard(trump: Suit) = cardsInHand
        .filter { it.suit == trump || (it.jokerSuit != null && it.jokerSuit.overheadedSuits.contains(trump)) }
        .sortedBy { it.speech.priority }
        .firstOrNull()

    fun takeCards(cards: List<Card>) = cardsInHand.addAll(cards)

}