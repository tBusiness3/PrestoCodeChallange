package com.example.codechallange;

import android.graphics.Color;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    AsyncManager led = new AsyncManager(this);
    Button btnRequestServer, btnCancel, btnPayNoReceipt, btnPayWithReceipt, btnReady;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPayNoReceipt = findViewById(R.id.btn_Pay);
        btnPayWithReceipt = findViewById(R.id.btn_requestRecept);
        btnRequestServer = findViewById(R.id.btn_summonServer);
        btnReady = findViewById(R.id.btn_readyToPay);
        btnCancel = findViewById(R.id.bt_cancle);
        textView = findViewById(R.id.tv_textViewFlashing);

        btnRequestServer.setOnClickListener(v -> requestServer());
        btnPayNoReceipt.setOnClickListener(v -> payNoReceipt());
        btnPayWithReceipt.setOnClickListener(v -> payWithReceipt());
        btnReady.setOnClickListener(v -> readyToPay());
        btnCancel.setOnClickListener(v -> cancelCall());
    }

    public void btnReset() {
        btnRequestServer.setEnabled(true);
        btnPayNoReceipt.setEnabled(true);
        btnPayWithReceipt.setEnabled(true);
        btnReady.setEnabled(true);
        btnCancel.setEnabled(true);

        btnRequestServer.setBackgroundColor(Color.WHITE);
        btnPayNoReceipt.setBackgroundColor(Color.WHITE);
        btnPayWithReceipt.setBackgroundColor(Color.WHITE);
        btnReady.setBackgroundColor(Color.WHITE);
        btnCancel.setBackgroundColor(Color.WHITE);
    }

    private void requestServer() {

        btnRequestServer.setEnabled(true);
        btnRequestServer.setBackgroundColor(Color.parseColor("#FF0000"));

        Set<LedColor> colorSet = new HashSet<>();
        colorSet.add(LedColor.COLOR_RED);

        led.turnOnLed(colorSet, true, -1);
    }

    private void payNoReceipt() {
        btnReset();

        btnPayNoReceipt.setEnabled(true);
        btnPayNoReceipt.setBackgroundColor(Color.parseColor("#008000"));

        Set<LedColor> colorSet = new HashSet<>();
        colorSet.add(LedColor.COLOR_GREEN);

        led.turnOnLed(colorSet, false, 180);
    }

    private void payWithReceipt() {
        btnReset();

        btnPayWithReceipt.setEnabled(true);
        btnPayWithReceipt.setBackgroundColor(Color.parseColor("#008000"));

        Set<LedColor> colorSet = new HashSet<>();
        colorSet.add(LedColor.COLOR_GREEN);

        led.turnOnLed(colorSet, true, 180);
    }

    private void readyToPay() {
        btnReset();

        btnReady.setEnabled(true);
        btnReady.setBackgroundColor(Color.parseColor("#FFA500"));

        Set<LedColor> colorSet = new HashSet<>();
        colorSet.add(LedColor.COLOR_ORANGE);

        led.turnOnLed(colorSet, false, 120);
    }

    public void cancelCall() {
        led.turnOffLed();
        btnReset();
    }

    public void txtViewFlash(Iterator color, int brightness) {
        if (brightness == 0) {
            textView.setBackgroundColor(Color.WHITE);
        } else {
            switch((LedColor)color.next()) {
                case COLOR_RED:
                    textView.setBackgroundColor(Color.parseColor("#FF0000"));
                    break;
                case COLOR_GREEN:
                    textView.setBackgroundColor(Color.parseColor("#008000"));
                    break;
                case COLOR_ORANGE:
                    textView.setBackgroundColor(Color.parseColor("#FFA500"));
                    break;
            }
        }
        }
}
