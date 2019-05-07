fun main() {
    val card1 = Card(Speech.JOKER, jokerSuit = JokerSuit.RED)

    println(card1.isGreaterThan(Card(Speech.JOKER, jokerSuit = JokerSuit.BLACK), Suit.SPADE))

}