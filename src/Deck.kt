class Deck() {
    val cards = Speech.values()
        .filter { it != Speech.JOKER }
        .flatMap { speech -> Suit.values().map { suit -> Card(speech, suit) } }
        .union(JokerSuit.values().map { Card(Speech.JOKER, jokerSuit = it) })
        .shuffled()
        .toMutableList()

    fun getSeveralCards(amount: Int): List<Card> {
        val receivedCards = cards.take(amount)
        cards.removeAll(receivedCards)
        return receivedCards
    }

}