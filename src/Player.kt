import java.util.Scanner

class Player(
    val cardsInHand: MutableList<Card>,
    val name: String,
    var attack: Boolean = false,
    var defend: Boolean = false
) {

    val scanner = Scanner(System.`in`)

    fun showCards() = cardsInHand.forEachIndexed { index, card -> println("${index + 1} - $card") }

    fun cardByIndex(index: Int): Card {
        return try {
            cardsInHand[index - 1]
        } catch (exception: IndexOutOfBoundsException) {
            println("Entered index is out of bounds, first card selected")
            cardsInHand[0]
        }
    }

    fun similarCards(tableCards: List<Card>) =
        cardsInHand.filter { tableCards.map { it.speech }.contains(it.speech) }

    fun greaterCards(attackingCard: Card, trump: Suit) = cardsInHand.filter { it.isGreaterThan(attackingCard, trump) }

    fun cardFromGreaterOrDefault(attackingCard: Card, trump: Suit): Card? {
        val greaterCards = greaterCards(attackingCard, trump)
        return if (greaterCards.isEmpty()) {
            println("There are no cards to defend")
            null
        } else chooseGreaterCard(greaterCards)
    }

    private fun chooseGreaterCard(greaterCards: List<Card>): Card {
        println("Choose Card")
        val chosedCard = cardByIndex(scanner.nextInt())
        return if (greaterCards.contains(chosedCard)) chosedCard else chooseGreaterCard(greaterCards)
    }

    fun lowestTrumpCardPriority(trump: Suit) = cardsInHand
        .filter { it.suit == trump || (it.jokerSuit != null && it.jokerSuit.overheadedSuits.contains(trump)) }
        .map { it.speech.priority }
        .sorted()
        .firstOrNull()

    fun takeCards(cards: List<Card>) = cardsInHand.addAll(cards)

    fun getCardsAmount() = cardsInHand.size

}