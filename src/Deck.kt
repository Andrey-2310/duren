class Deck {

    val cards: MutableList<Card>
    val trump: Suit

    init {
        cards = Speech.values()
            .filter { it != Speech.JOKER }
            .flatMap { speech -> Suit.values().map { suit -> Card(speech, suit) } }
            .union(JokerSuit.values().map { Card(Speech.JOKER, jokerSuit = it) })
            .toMutableList()

        trump = findNewTrump()
    }

    fun getSeveralCards(amount: Int): List<Card> {
        val receivedCards = cards.take(amount)
        cards.removeAll(receivedCards)
        return receivedCards
    }

    private fun findNewTrump(): Suit {
        cards.shuffle()
        return cards.last().suit ?: findNewTrump()
    }

}