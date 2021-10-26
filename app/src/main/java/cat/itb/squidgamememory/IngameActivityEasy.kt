package cat.itb.squidgamememory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton

class IngameActivityEasy : AppCompatActivity() {
    private var cards = mutableListOf<ImageButton>()
    //TODO: Add sprites to project and initialize array with ids
    private var boardValues = arrayOf(0, 0, 1, 1, 2, 2)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingame_easy)

        boardValues.shuffle()
        for (index in 1..6){
            cards.add(findViewById(resources.getIdentifier("card$index", "id", packageName)))
            cards[index-1].setOnClickListener { processButtonClick(it, index) }
        }

    }

    // Cada cop que es clica un botó aquest mètode rep la View del botó i l'índex d'aquest
    fun processButtonClick(cardButton: View, index: Int){
        Log.d("DEBUG", cardButton.toString())
        Log.d("DEBUG", index.toString())
    }
}