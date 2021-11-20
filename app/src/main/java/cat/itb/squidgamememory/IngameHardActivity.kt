package cat.itb.squidgamememory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider

class IngameHardActivity : AppCompatActivity() {
    private var buttons = mutableListOf<ImageButton>()
    private lateinit var timerText : TextView
    private lateinit var moveCounter : TextView
    private lateinit var gameOverText : TextView
    private lateinit var pauseText : TextView
    private lateinit var pauseButton: ImageButton
    private lateinit var viewModel : IngameHardViewModel
    private lateinit var timer: CountDownTimer
    private lateinit var shakeAnimation: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingame_hard)

        timerText = findViewById(R.id.game_timer)
        viewModel = ViewModelProvider(this).get(IngameHardViewModel::class.java)
        moveCounter = findViewById(R.id.move_counter)
        gameOverText = findViewById(R.id.game_over_text)
        pauseText = findViewById(R.id.pause_text)
        pauseButton = findViewById(R.id.pause_button)
        shakeAnimation = AnimationUtils.loadAnimation(this, R.anim.shake)

        for (i in 0..8) {
            buttons.add(findViewById(viewModel.cards[i].buttonId))
            buttons[i].setOnClickListener{ if(!viewModel.isOver and !viewModel.isPaused) processButtonClick(i) }
        }

        pauseButton.setOnClickListener { if(!viewModel.isOver) pauseButtonLogic() }

        updateUI()
    }

    private fun processButtonClick(i: Int){
        if(!viewModel.cards[i].girada or viewModel.wrongMatch){
            if(viewModel.wrongMatch)
                undoLastFlip()
            if(viewModel.first == null)
                primerClick(i)
            else if (viewModel.second == null)
                segonClick(i)
            else
                tercerClick(i)
        }else
            Toast.makeText(applicationContext, getString(R.string.invalid_move_text), Toast.LENGTH_SHORT).show()
    }

    private fun primerClick(i: Int) {
        viewModel.first = i
        flipCard(i)
    }

    private fun segonClick(i: Int) {
        viewModel.second = i
        flipCard(i)
    }

    private fun tercerClick(i: Int){
        viewModel.third = i
        flipCard(i)
        addMove()
        if(viewModel.checkIfMatch()){
            findViewById<ImageButton>(viewModel.cards[viewModel.first!!].buttonId).startAnimation(shakeAnimation)
            findViewById<ImageButton>(viewModel.cards[viewModel.second!!].buttonId).startAnimation(shakeAnimation)
            findViewById<ImageButton>(viewModel.cards[viewModel.third].buttonId).startAnimation(shakeAnimation)
            viewModel.first = null
            viewModel.second = null
            if(viewModel.matches == 3)
                goToFinishActivity()
        }
    }

    private fun flipCard(i: Int){
        val button = findViewById<ImageButton>(viewModel.cards[i].buttonId)
        button.setImageResource(viewModel.flipCard(i))
    }

    private fun undoLastFlip(){
        flipCard(viewModel.first!!)
        flipCard(viewModel.second!!)
        flipCard(viewModel.third)
        viewModel.undoLastFlip()
    }

    private fun addMove(){
        viewModel.moves++
        updateMoveCounter()
    }

    private fun updateUI() {
        for(i in 0..5)
            buttons[i].setImageResource(viewModel.getCardImage(i))
        updateMoveCounter()

        if(viewModel.isPaused) {
            resume()
            pause()
        }else
            resume()
    }

    private fun updateMoveCounter(){
        moveCounter.text = getString(R.string.move_counter_text, viewModel.moves)
    }

    private fun goToFinishActivity(){
        viewModel.isOver = true
        timer.cancel()
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra("Moves", viewModel.moves)
        intent.putExtra("Matches", viewModel.matches)
        intent.putExtra("Time", viewModel.time)
        intent.putExtra("Difficulty", 2)
        Handler(Looper.getMainLooper()).postDelayed( { startActivity(intent); finish() }, 2000)
    }

    private fun startTimer(){
        timerText.text = viewModel.time.toString()
        timer = object : CountDownTimer((viewModel.time*1000).toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                viewModel.time--
                timerText.text = viewModel.time.toString()
            }

            override fun onFinish() {
                gameOverText.visibility = View.VISIBLE
                goToFinishActivity()
            }
        }.start()
    }

    private fun pauseButtonLogic(){
        if(viewModel.isPaused)
            resume()
        else
            pause()
    }

    private fun pause(){
        timer.cancel()
        pauseText.visibility = View.VISIBLE
        viewModel.isPaused = true
    }

    private fun resume(){
        startTimer()
        pauseText.visibility = View.INVISIBLE
        viewModel.isPaused = false
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}