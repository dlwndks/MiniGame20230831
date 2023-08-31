package com.example.minigames;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minigames.databinding.ActivityGame2Binding;

import java.util.ArrayList;
import java.util.Random;

public class Game2 extends AppCompatActivity implements View.OnClickListener, NumberPicker.OnScrollListener {
    ActivityGame2Binding binding;

    ArrayList<Integer> myNumberList = new ArrayList<>();  //내가 선택한 공
    ArrayList<Integer> randomNumberList = new ArrayList<>();  //로또기계가 뽑은 공
    //textVIew를 리스트로 관리하자
    ArrayList<TextView> myNumberTextView = new ArrayList<>();
    ArrayList<TextView> randomNumverTextView = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGame2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //스크롤에 1부터 45의 숫자가 나오도록 하자
        binding.numberPicker.setMinValue(1);
        binding.numberPicker.setMaxValue(45);
        Random r = new Random();
        binding.numberPicker.setValue(r.nextInt(45)+1);

        //공을 그리는 TextView를 리스트에 관리하자
        myNumberTextView.add(binding.myBall1);
        myNumberTextView.add(binding.myBall2);
        myNumberTextView.add(binding.myBall3);
        myNumberTextView.add(binding.myBall4);
        myNumberTextView.add(binding.myBall5);
        myNumberTextView.add(binding.myBall6);

        randomNumverTextView.add(binding.robotBall1);
        randomNumverTextView.add(binding.robotBall2);
        randomNumverTextView.add(binding.robotBall3);
        randomNumverTextView.add(binding.robotBall4);
        randomNumverTextView.add(binding.robotBall5);
        randomNumverTextView.add(binding.robotBall6);

        binding.numberPicker.setOnScrollListener(this);
        binding.btnAdd.setOnClickListener(this);
        binding.btnClear.setOnClickListener(this);
        binding.btnRun.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_add:
                addOneBall();//공뽑아서 리스트에 저장
                //화면에 표시
                for(int i = 0; i<myNumberList.size(); i++){
                    myNumberTextView.get(i).setVisibility(view.VISIBLE);
                    myNumberTextView.get(i).setText(Integer.toString(myNumberList.get(i)));
                }

                break;
            case R.id.btn_clear:
                for(int i = 0; i<myNumberList.size(); i++){
                    myNumberTextView.get(i).setVisibility(view.INVISIBLE);
                    myNumberTextView.get(i).setText("0");
                }
                myNumberList.clear();
                break;
            case R.id.btn_run:
                //랜덤 숫자 뽑고 화면에 표시
                getRandomNumber();
                int win=0;

                for (Integer a: myNumberList      ) {
                    if(randomNumberList.contains(a)){  //일치
                        win++;
                    }
                }
                binding.textMessage.setText("당첨수 : " + win);
                break;
        }
    }

    private void getRandomNumber() {
        Random r = new Random();
        Integer number;
        //이전 작업에서 randomNumberList의 사이즈가 6이면 clear
        if (randomNumberList.size()==6) {
            randomNumberList.clear();
        }

        //6개의 랜덤한 숫자를 만들어서 randomNumberList에 추가한다.
        while (randomNumberList.size() < 6){
            number = r.nextInt(45)+1;

            if (myNumberList.contains(number)){  //이때 중복되는 값이 있는지 확인한다.
                continue;
            }
            randomNumverTextView.get(randomNumberList.size()).setText(number.toString());
            randomNumberList.add(number);
        }

    }

    private void addOneBall() {
        //숫자를 리스트에 저장
        if(myNumberList.size()==6) {
            Toast.makeText(this, "6개 모두 선택했습니다.", Toast.LENGTH_SHORT).show();
            binding.numberPicker.setEnabled(false);
        }
        int number = binding.numberPicker.getValue(); //선택된 값 가지고오기
        if (myNumberList.contains(number)){
            Toast.makeText(this, "이미 선택한 숫자입니다.", Toast.LENGTH_SHORT).show();
        } else{
            myNumberList.add(number);
        }
    }

    @Override
    public void onScrollStateChange(NumberPicker numberPicker, int i) {
        if (binding.switch1.isChecked()){
            if (i == SCROLL_STATE_IDLE)
                binding.btnAdd.performClick();
        }
    }
}