package cat.itb.squidgamememory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import kotlin.math.pow

class ResultActivity : AppCompatActivity() {
    private lateinit var scoreText : TextView
    private lateinit var perfectText: TextView
    private lateinit var replayButton: ImageButton
    private lateinit var homeButton: ImageButton
    private lateinit var shareButton: Button
    private var moves = 0
    private var matches = 0
    private var time = 0
    private var difficulty = 0
    private var score = 0;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        scoreText = findViewById(R.id.score_text)
        perfectText = findViewById(R.id.perfect_score)
        replayButton = findViewById(R.id.replay_button)
        homeButton = findViewById(R.id.home_button)
        shareButton = findViewById(R.id.share_button)
        moves = intent.extras?.getInt("Moves")!!
        matches = intent.extras?.getInt("Matches")!!
        time = intent.extras?.getInt("Time")!!
        difficulty = intent.extras?.getInt("Difficulty")!!

        score = dataToScore()
        scoreText.text = getString(R.string.score_text, score)
        checkPerfect()

        homeButton.setOnClickListener { startActivity(Intent(this, MainActivity::class.java)); finish() }
        replayButton.setOnClickListener {
            when(intent.extras?.getInt("Difficulty")){
                1 -> startActivity(Intent(this, IngameEasyActivity::class.java))
                2 -> startActivity(Intent(this, IngameNormalActivity::class.java))
                3 -> startActivity(Intent(this, IngameHardActivity::class.java))
            }
            finish()
        }
        shareButton.setOnClickListener {
            val shareIntent = Intent()
            shareIntent.action = Intent.ACTION_SEND
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_text, score))
            startActivity(Intent.createChooser(shareIntent, getString(R.string.send_to)))
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
        val bestCase = when(difficulty){
            1, 3 -> 3
            2 -> 4
            else -> 0
        }
        return ((matches * 200f/bestCase) + (800 * 0.95.pow(moves - bestCase) * (time.toFloat()/30))).toInt()
    }
}