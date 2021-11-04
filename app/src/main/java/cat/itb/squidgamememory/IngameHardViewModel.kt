package cat.itb.squidgamememory

import androidx.lifecycle.ViewModel

class IngameHardViewModel : ViewModel() {
    private var buttons = arrayOf(R.id.card1, R.id.card2, R.id.card3, R.id.card4, R.id.card5, R.id.card6, R.id.card7, R.id.card8, R.id.card9)
    private var images = arrayOf(R.drawable.circle, R.drawable.star, R.drawable.triangle, R.drawable.circle, R.drawable.star, R.drawable.triangle, R.drawable.circle, R.drawable.star, R.drawable.triangle)
    var cards = arrayListOf<CookieCard>()

    var wrongMatch = false
    var isOver = false
    var isPaused = false
    var first : Int? = null
    var second : Int? = null
    var third = 0
    var moves = 0
    var matches = 0
    var time = 30

    init {
        setDataModel()
    }

    private fun setDataModel(){
        images.shuffle()
        for (i in 0..8)
            cards.add(CookieCard(buttons[i], images[i]))
    }

    fun flipCard(i: Int): Int{
        cards[i].girada = !cards[i].girada
        return getCardImage(i)
    }

    fun checkIfMatch(): Boolean{
        if(images[first!!] == images[second!!] && images[first!!] == images[third]){
            first = null
            second = null
            matches++
        }else
            wrongMatch = true
        return matches == 3
    }

    fun undoLastFlip(){
        first = null
        second = null
        wrongMatch = false
    }

    fun getCardImage(i: Int): Int{
        return if (cards[i].girada) images[i] else R.drawable.empty
    }
}