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
        println("Trump: ${trump.sign}")
    }

    fun getSeveralCards(amount: Int): MutableList<Card> {
        val receivedCards = cards.take(amount)
        cards.removeAll(receivedCards)
        return receivedCards.toMutableList()
    }

    private fun findNewTrump(): Suit {
        cards.shuffle()
        return cards.last().suit ?: findNewTrump()
    }

}