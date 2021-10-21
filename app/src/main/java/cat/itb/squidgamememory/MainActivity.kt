package cat.itb.squidgamememory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private lateinit var playButton : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_SquidGameMemory)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        playButton = findViewById(R.id.play_button)

        playButton.setOnClickListener(){
            startEasy()
        }
    }

    private fun startEasy() {
        val intent = Intent(this, IngameActivityEasy::class.java)
        startActivity(intent)
    }
}