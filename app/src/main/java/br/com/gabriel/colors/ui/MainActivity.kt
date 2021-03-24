package br.com.gabriel.colors.ui

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SeekBar
import android.widget.TextView
import br.com.gabriel.colors.R
import br.com.gabriel.colors.R.layout
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class MainActivity : AppCompatActivity() {
    private lateinit var seekBar:SeekBar
    private lateinit var view:View
    private lateinit var listSeekBar:ArrayList<SeekBar>
    private lateinit var textView: TextView
    private var hashMap:HashMap<String,Int>? = null
    private var arrayIndex = Array<Int>(3){-1}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout.activity_main)
        setTitle("Choose your color")
        initializeComponents()
        getValuesSeekBar()
    }

    fun initializeComponents(){
        view = findViewById(R.id.activity_view)
        textView = findViewById(R.id.activity_textView)
        listSeekBar = ArrayList<SeekBar>()
        listSeekBar.add(findViewById(R.id.activity_seekBar1))
        listSeekBar.add(findViewById(R.id.activity_seekBar2))
        listSeekBar.add(findViewById(R.id.activity_seekBar3))
    }

    private fun getValuesSeekBar(){
        if(hashMap == null){setHashMapAndArray()}

        listSeekBar.forEach { it->
            it.setOnSeekBarChangeListener(object:
                    SeekBar.OnSeekBarChangeListener {

                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    var value: Int = (255 * seekBar?.progress!!)/100
                    hashMap?.set(it.id.toString(),value)
                    setColorView()
                    setTextView()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {}

                override fun onStopTrackingTouch(seekBar: SeekBar?) {}

            })
        }
    }

    private fun setHashMapAndArray(){
        var index:Int = 0
        hashMap = HashMap()
        listSeekBar.forEach{
            hashMap?.put(it.id.toString(),0)
            arrayIndex[index++] = it.id
        }
    }

    private fun setColorView(){
        view.setBackgroundColor(Color.rgb
                    (hashMap?.get(arrayIndex[0].toString())!!
                     , hashMap?.get(arrayIndex[1].toString())!!
                     , hashMap?.get(arrayIndex[2].toString())!!))
    }

    private fun setTextView(){
        var hexadecimal = hashMap?.get(arrayIndex[0].toString())!!.toLong().toString(16) +
                hashMap?.get(arrayIndex[1].toString())!!.toLong().toString(16)+
                hashMap?.get(arrayIndex[2].toString())!!.toLong().toString(16)

        when(hexadecimal){
               "000" ->  textView.setText("#${hexadecimal.toUpperCase()}000")
                else -> {textView.setText("#${hexadecimal.toUpperCase()}")}
            }
    }
}

