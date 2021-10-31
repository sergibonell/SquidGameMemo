package cat.itb.squidgamememory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast

class IngameHardActivity : AppCompatActivity() {
    private var cards = mutableListOf<CookieCard>()
    private var buttons = arrayOf(R.id.card1, R.id.card2, R.id.card3, R.id.card4, R.id.card5, R.id.card6, R.id.card7, R.id.card8, R.id.card9)
    private var images = arrayOf(R.drawable.circle, R.drawable.star, R.drawable.triangle, R.drawable.circle, R.drawable.star, R.drawable.triangle,R.drawable.circle, R.drawable.star, R.drawable.triangle)

    private var wrongMatch = false
    private var first : CookieCard? = null
    private var second : CookieCard? = null
    private lateinit var third : CookieCard
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingame_hard)

        images.shuffle()
        for (index in 0..8) {
            cards.add(CookieCard(buttons[index], images[index]))
            findViewById<ImageButton>(buttons[index]).setOnClickListener { processButtonClick(cards[index]) }
        }
    }

    private fun processButtonClick(cookieCard: CookieCard){
        if(!cookieCard.girada){
            if(wrongMatch)
                undoLastMatch()

            if(first == null)
                primerClick(cookieCard)
            else if(second == null)
                segonClick(cookieCard)
            else
                tercerClick(cookieCard)
        }else
            Toast.makeText(applicationContext, "Invalid move", Toast.LENGTH_SHORT).show()
    }

    private fun primerClick(cookieCard: CookieCard) {
        first = cookieCard
        flipCard(first!!)
    }

    private fun segonClick(cookieCard: CookieCard) {
        second = cookieCard
        flipCard(second!!)
    }

    private fun tercerClick(cookieCard: CookieCard) {
        third = cookieCard
        flipCard(third)
        if(first!!.imageId == second!!.imageId && first!!.imageId == third.imageId){
            score++
            first = null
            second = null
        }
        else
            wrongMatch = true
    }

    private fun flipCard(cookieCard: CookieCard){
        val button = findViewById<ImageButton>(cookieCard.buttonId)

        if(cookieCard.girada)
            button.setImageResource(R.drawable.empty)
        else
            button.setImageResource(cookieCard.imageId)

        cookieCard.girada = !cookieCard.girada
    }

    private fun undoLastMatch(){
        flipCard(first!!)
        flipCard(second!!)
        flipCard(third)
        first = null
        second = null
        wrongMatch = false
    }
}