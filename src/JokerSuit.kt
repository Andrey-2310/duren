import Suit.CLUBS
import Suit.DIAMONDS
import Suit.HEARTS
import Suit.SPADE

enum class JokerSuit(val overheadedSuits: List<Suit>) {

    RED(listOf(DIAMONDS, HEARTS)),
    BLACK(listOf(CLUBS, SPADE));

    fun isJokerOverheadSuit(suit: Suit) = overheadedSuits.contains(suit)

}