class Card(val speech: Speech, val suit: Suit? = null, val jokerSuit: JokerSuit? = null) {
    override fun toString(): String = "${speech} ${suit ?: jokerSuit}"
}