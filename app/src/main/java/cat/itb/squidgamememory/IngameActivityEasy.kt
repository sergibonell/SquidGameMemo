package cat.itb.squidgamememory

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton

class IngameActivityEasy : AppCompatActivity(), View.OnClickListener {
    private var indexImatges = arrayOf(0, 0, 1, 1, 2, 2)
    private var imatgesGaletes = arrayOf(R.drawable.galeta1, R.drawable.galeta2, R.drawable.galeta3)
    private var galetes = mutableListOf<ImageButton>()
    private var currentGaleta : ImageButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingame_easy)

        indexImatges.shuffle()
        for (i in 1..6) {
            galetes.add(findViewById(resources.getIdentifier("cookie$i", "id", packageName)))
            galetes[i-1].setOnClickListener(this)
        }
    }

    override fun onClick(p0: View?) {
        var galeta : ImageButton = p0 as ImageButton
        galeta.setImageResource(imatgesGaletes[getImageIndexFromView(galeta)])

        if(currentGaleta == null){
            currentGaleta = galeta
        }else{
            if(getImageIndexFromView(currentGaleta!!) != getImageIndexFromView(galeta)){
                galeta.setImageResource(imatgesGaletes[getImageIndexFromView(galeta)])
                Thread.sleep(1000)
                currentGaleta!!.setImageResource(R.drawable.empty)
                galeta.setImageResource(R.drawable.empty)
            }
            currentGaleta = null;
        }
    }

    fun getButtonIndexFromView(x: View) : Int{
        return resources.getResourceEntryName(x.id).last().digitToInt()-1
    }

    fun getImageIndexFromView(x: View) : Int{
        return indexImatges[getButtonIndexFromView(x)]
    }
}