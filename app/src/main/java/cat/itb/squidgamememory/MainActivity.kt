package cat.itb.squidgamememory

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var difficultyRadioGroup: RadioGroup
    private lateinit var playButton : Button
    private lateinit var helpButton: FloatingActionButton
    private lateinit var helpDialog: Dialog
    private var difficulty = Difficulties.None


    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_SquidGameMemory)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        playButton = findViewById(R.id.play_button)
        difficultyRadioGroup = findViewById(R.id.difficulty_radio_group)
        helpButton = findViewById(R.id.help_button)

        helpDialog = AlertDialog.Builder(this, R.style.AlertDialog)
            .setTitle(R.string.help_dialog_title)
            .setMessage(R.string.help_dialog_message)
            .setPositiveButton(R.string.help_dialog_ok){
                view, _ -> view.dismiss()
            }
            .setCancelable(true)
            .create()

        difficultyRadioGroup.setOnCheckedChangeListener{ _, checkedId ->
            if (checkedId == R.id.easy_radio)
                difficulty = Difficulties.Easy
            if (checkedId == R.id.medium_radio)
                difficulty = Difficulties.Medium
            if (checkedId == R.id.hard_radio)
                difficulty = Difficulties.Hard
        }

        playButton.setOnClickListener(){
            startGame()
        }

        helpButton.setOnClickListener(){
            helpDialog.show()
        }
    }

    private fun startGame() {
        when (difficulty) {
            Difficulties.Easy -> startActivity(Intent(this, IngameEasyActivity::class.java))
            Difficulties.Medium -> startActivity(Intent(this, IngameNormalActivity::class.java))
            Difficulties.Hard -> startActivity(Intent(this, IngameHardActivity::class.java))
            Difficulties.None -> Toast.makeText(applicationContext, "Select a difficulty", Toast.LENGTH_SHORT).show()
        }
    }

    enum class Difficulties{
        Easy,
        Medium,
        Hard,
        None
    }
}