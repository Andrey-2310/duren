class Card(val speech: Speech, val suit: Suit? = null, val jokerSuit: JokerSuit? = null) {
    override fun toString(): String = "$speech${suit?.sign ?: jokerSuit}"

    fun isGreaterThan(card: Card, trump: Suit) =
        isGreaterWithSameSuit(card) || isTrumpUnlikeAnother(card, trump) || isJokerOverOtherCard(card)

    private fun isGreaterWithSameSuit(card: Card) =
        this.suit != null && this.suit == card.suit && this.speech.isGreaterThan(card.speech)

    private fun isTrumpUnlikeAnother(card: Card, trump: Suit) =
        this.suit != null && card.suit != null && this.suit == trump && card.suit != trump

    private fun isJokerOverOtherCard(card: Card) =
            this.jokerSuit != null && card.suit != null && this.jokerSuit.isJokerOverheadSuit(card.suit)

}