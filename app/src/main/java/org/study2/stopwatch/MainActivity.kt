package org.study2.stopwatch

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.study2.stopwatch.databinding.ActivityMainBinding
import java.util.*
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private var time = 0
    private var timerTask: Timer? = null
    private var isRunning = false
    private var lap = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.fab.setOnClickListener {
            isRunning = !isRunning

            if (isRunning) {
                start()
            } else {
                pause()
            }
        }
        binding.lapButton.setOnClickListener {
            recordLapTime()
        }
        binding.resetFab.setOnClickListener {
            reset()
        }
    }

    private fun start() {
//        이미지를 정지 이미지로 변경
        binding.fab.setImageResource(R.drawable.ic_baseline_pause_24)

        /*timer(period = 1000) {
            오래걸리는 작업
                    runOnUiThread {
                        Contacts.Intents.UI 변경작업
                    }
        }*/

        /* 나중에 timer를 취소하려면 timer를 실행하고 반환되는 timer객체를 면수에 저장해둘 필요가 있습니다.
         이를 위해 timerTast변수를 null을 허용하는 Timer 타입으로 선언했습니다.*/

        timerTask = timer(period = 10) {
            time++
            val sec = time / 100
            val milli = time % 100
            runOnUiThread {
                binding.secTextView.text = "$sec"
                binding.milliTextView.text = "$milli"
            }
        }
    }

    private fun pause() {
        binding.fab.setImageResource(R.drawable.ic_baseline_play_arrow_24)
        timerTask?.cancel()
    }

    /*LivearLayout에 동적으로 뷰를 추가하기
    val textView = TextView(this)
    textView.text = "글자"
    lapLayout.addViw(textView)*/

    private fun recordLapTime() {
        val lapTime = this.time
        val textView = TextView(this)
        textView.text = "$lap LAP : ${lapTime / 100}.${lapTime % 100}"

//        맨 위에 랩타임 추가
        binding.lapLayout.addView(textView, 0)
        lap++
    }

    private fun reset() {
//        실행하고 있는 타이머가 있으면 취소
        timerTask?.cancel()
//        모든 변수 초기화
        time = 0
        isRunning = false
        binding.fab.setImageResource(R.drawable.ic_baseline_play_arrow_24)
        binding.secTextView.text = "0"
        binding.milliTextView.text = "00"

//        모든 랩타임 제거
        binding.lapLayout.removeAllViews()
        lap = 1
    }
}