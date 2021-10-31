package cat.itb.squidgamememory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView

class ResultActivity : AppCompatActivity() {
    private lateinit var scoreText : TextView
    private lateinit var perfectText: TextView
    private lateinit var replayButton: ImageButton
    private lateinit var homeButton: ImageButton
    private var moves = 0
    private var matches = 0
    private var time = 0
    private var difficulty = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        scoreText = findViewById(R.id.score_text)
        perfectText = findViewById(R.id.perfect_score)
        replayButton = findViewById(R.id.replay_button)
        homeButton = findViewById(R.id.home_button)
        moves = intent.extras?.getInt("Moves")!!
        matches = intent.extras?.getInt("Matches")!!
        time = intent.extras?.getInt("Time")!!
        difficulty = intent.extras?.getInt("Difficulty")!!

        scoreText.text = getString(R.string.score_text, dataToScore())
        checkPerfect()

        homeButton.setOnClickListener { startActivity(Intent(this, MainActivity::class.java)) }
        replayButton.setOnClickListener {
            when(intent.extras?.getInt("Difficulty")){
                1 -> startActivity(Intent(this, IngameEasyActivity::class.java))
                2 -> startActivity(Intent(this, IngameNormalActivity::class.java))
                3 -> startActivity(Intent(this, IngameHardActivity::class.java))
            }
        }
    }

    private fun checkPerfect(){
        if (when(difficulty){
            1, 3 -> moves == 3
            2 -> moves == 4
            else -> false
        })
            perfectText.visibility = View.VISIBLE
    }

    private fun dataToScore(): Int{
        return matches * 100 + time * 10 - moves * 30
    }
}