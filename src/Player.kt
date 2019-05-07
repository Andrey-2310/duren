class Player(val cardsInHand: List<Card>) {

    fun showCards() = cardsInHand.forEachIndexed { index, card -> println("${index + 1} - $card")}

    fun getCard(index: Int): Card {
        try {
            return cardsInHand[index-1]
        } catch (exception: IndexOutOfBoundsException) {
            println("Entered index is out of bounds, first card selected")
            return cardsInHand[0]
        }
    }

    fun showSimilarCards(tableCards: List<Card>) =
        cardsInHand.filter {tableCards.map { it.speech }.contains(it.speech) }

    fun showGreaterCards(card: Card, trump: Suit) = cardsInHand.filter { it.isGreaterThan(card, trump) }

}