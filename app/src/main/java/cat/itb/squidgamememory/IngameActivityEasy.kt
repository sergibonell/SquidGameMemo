package cat.itb.squidgamememory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class IngameActivityEasy : AppCompatActivity() {
    private lateinit var cookies : Array<ImageButton>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingame_easy)

        for (i in 1..6)
            cookies[i] = findViewById(resources.getIdentifier("cookie$i", "drawable", packageName))

        print("debug")
    }
}