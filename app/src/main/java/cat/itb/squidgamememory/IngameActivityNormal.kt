package cat.itb.squidgamememory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast

class IngameActivityNormal : AppCompatActivity() {
    private var cards = mutableListOf<CookieCard>()
    private var buttons = arrayOf(R.id.card1, R.id.card2, R.id.card3, R.id.card4, R.id.card5, R.id.card6, R.id.card7, R.id.card8)
    private var images = arrayOf(R.drawable.circle, R.drawable.star, R.drawable.triangle, R.drawable.umbrella, R.drawable.circle, R.drawable.star, R.drawable.triangle, R.drawable.umbrella)

    private var wrongMatch = false
    private var first : CookieCard? = null
    private lateinit var second : CookieCard
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingame_normal)

        images.shuffle()
        for (index in 0..7) {
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
            else
                segonClick(cookieCard)
        }else
            Toast.makeText(applicationContext, "Invalid move", Toast.LENGTH_SHORT).show()
    }

    private fun primerClick(cookieCard: CookieCard) {
        first = cookieCard
        flipCard(first!!)
    }

    private fun segonClick(cookieCard: CookieCard) {
        second = cookieCard
        flipCard(second)
        if(first!!.imageId == second.imageId){
            score++
            first = null
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
        flipCard(second)
        first = null
        wrongMatch = false
    }
}