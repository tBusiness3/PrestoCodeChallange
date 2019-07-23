package com.example.codechallange;

import android.os.Handler;
import android.os.Message;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class AsyncManager {
    static String CANCEL = "CANCEL";
     LedManager led;
     MainActivity mainActivity;
     android.os.Handler ledHandler;

    AsyncManager(MainActivity activity) {
        this.mainActivity = activity;

        led = new LedManager();
        ledHandler = new CustomHandler(this);
    }

    AsyncManager() {
        led = new LedManager();
        ledHandler = new CustomHandler(this);
    }

    private class SimpleLedThread extends Thread {
        public void run() {
            turnOffLed();
        }
    }

    protected int findBrightness() {
        return led.deviceHardware.getCurrentBrightness();
    }

    protected Set<LedColor> findColors() {
        return led.deviceHardware.getCurrentLedColors();
    }
    void turnOnLed(Set<LedColor> colorSet, boolean flashing, int terminateTimeInSecs) {
        led.setLedColors(colorSet, 100);
        if (flashing) {

            ledHandler.post(new LedThread(colorSet));
        }

        if (terminateTimeInSecs > 0)
            ledHandler.postDelayed(new SimpleLedThread(), terminateTimeInSecs * 1000);
    }

    void turnOffLed() {
        Message msg = new Message();
        msg.obj = CANCEL;
        ledHandler.sendMessage(msg);
    }
    protected class LedThread extends Thread {
        private Set<LedColor> colors;
        private int currentBrightness;

        LedThread(Set<LedColor> colors) {
            this.colors = colors;
            this.currentBrightness = 0;
        }

        public void run() {
            if (currentBrightness == 0){
                currentBrightness = 100;
            }
            else
                currentBrightness = 0;
            led.setLedColors(colors, currentBrightness);
            Iterator iter = colors.iterator();
            mainActivity.txtViewFlash(iter,currentBrightness);
            ledHandler.postDelayed(this, 1000);
        }
    }
    private void restart() {
        if(mainActivity != null)
            mainActivity.btnReset();
        led.setLedColors(new HashSet<>(), 0);
        ledHandler.removeCallbacksAndMessages(null);
    }

    static class CustomHandler extends Handler {
        AsyncManager manager;

        CustomHandler(AsyncManager manager) {
            super();
            this.manager = manager;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.obj == CANCEL) {
                manager.restart();
            }
        }
    }
}
