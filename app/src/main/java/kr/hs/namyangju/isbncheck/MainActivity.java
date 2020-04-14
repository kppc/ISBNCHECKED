package kr.hs.namyangju.isbncheck;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    EditText input_ISBN;
    Button send_btn;
    TextView statusViewISBN, statusView;

    int[] secondTask = {1, 3, 1, 3, 1, 3, 1, 3, 1, 3, 1, 3};
    int[] firstTask = new int[12];
    int[] subStringInt = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};

    int[] secondTaskVal = new int[12];
    int thirdTaskTotalVal = 0;
    int fourthTaskDivideVal = 0;
    int fifthTaskTotalVal = 0;
    double sixthTaskValidityVal = 0.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        validity();

        send_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(input_ISBN.getText().equals(""))) {
                    statusViewISBN.setText(input_ISBN.getText().toString());
                    ISBN_VALIDITY();
                }
            }
        });
    }

    public void ISBN_VALIDITY() {
            initVal();
            FIRST_SEQUENCE(input_ISBN.getText().toString());
            SECOND_SEQUENCE();
            THIRD_SEQUENCE();
            FOURTH_SEQUENCE();
            FIFTH_SEQUENCE();
            SIXTH_SEQUENCE();
            SEVENTH_SEQUENCE();
    }

    public void FIRST_SEQUENCE(String ISBN_ID) {
        int count = 0;
        for (int i = 0; i <= 13; i++) {
            count++;
            firstTask[i] = Integer.parseInt(ISBN_ID.substring(subStringInt[i], i + 1));
            Log.v("LOG", i + ": " + firstTask[i]);
            if (count >= 12) {
                return;
            }
        }
    }

    public void SECOND_SEQUENCE() {
        for (int i = 0; i < firstTask.length; i++) {
            secondTaskVal[i] = firstTask[i] * secondTask[i];
            Log.v("LOG", firstTask[i] + "*" + secondTask[i] + ": " + secondTaskVal[i]);
        }
    }

    public void THIRD_SEQUENCE() {
        for (int i = 0; i < firstTask.length; i++) {
            thirdTaskTotalVal += secondTaskVal[i];
        }
        Log.v("LOG", "3단계 값 : " + thirdTaskTotalVal);
    }

    public void FOURTH_SEQUENCE() {
        fourthTaskDivideVal = thirdTaskTotalVal % 10;
        Log.v("LOG", "4단계 값 : " + fourthTaskDivideVal);
    }

    public void FIFTH_SEQUENCE() {
        fifthTaskTotalVal = 10 - (int) fourthTaskDivideVal;
        Log.v("LOG", "5단계 값 : " + fifthTaskTotalVal);
    }

    public void SIXTH_SEQUENCE() {
        sixthTaskValidityVal = (thirdTaskTotalVal + fourthTaskDivideVal) % 10.0;
        Log.v("LOG", "6단계 값 : " + sixthTaskValidityVal);
    }

    public void SEVENTH_SEQUENCE() {
        if (!(sixthTaskValidityVal == 0)) {
            statusView.setText("올바르지 않은 ISBN 번호 입니다.");
            statusView.setTextColor(Color.parseColor("#0067A3"));
            statusView.setTextSize(16);
        } else {
            statusView.setText("올바른 ISBN 번호 입니다.");
            statusView.setTextColor(Color.parseColor("#0067A3"));
            statusView.setTextSize(16);
        }
    }

    //TODO(유효성) ISBN 입력부분에서 글자를 가져와서 13자리를 고정해주고, 공백인 것을 체크해 준다.
    public void validity() {
        input_ISBN.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (input_ISBN.getText().equals("")) {
                    input_ISBN.setError("ISBN 13자리 숫자를 입력하여 주십시오");
                }
                if (input_ISBN.length() > 13) {
                    input_ISBN.setError("13자리 미만입니다. 13자리를 맞추어 주십시오");
                }
                if (input_ISBN.length() < 13) {
                    input_ISBN.setError("13자리 초과입니다. 13자리를 맞추어 주십시오");
                }
            }
        });
    }

    public void initVal() {
        thirdTaskTotalVal = 0;
        fourthTaskDivideVal = 0;
        fifthTaskTotalVal = 0;
    }

    public void init() {
        input_ISBN = (EditText) findViewById(R.id.inputISBN);
        send_btn = (Button) findViewById(R.id.send);
        statusView = (TextView) findViewById(R.id._status);
        statusViewISBN = (TextView) findViewById(R.id.status);
    }
}
