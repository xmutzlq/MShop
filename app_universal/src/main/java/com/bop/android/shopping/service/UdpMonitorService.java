package com.bop.android.shopping.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UdpMonitorService extends Service {

    private boolean isRunning = false;
    private final static int PORT = 13;

    @Override
    public void onCreate() {
        super.onCreate();
        isRunning = true;
        startTimeThread();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    private void startTimeThread(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRunning) {
                    try (DatagramSocket socket = new DatagramSocket(PORT)) {
                        DatagramPacket request = new DatagramPacket(new byte[1024], 1024);
                    } catch (IOException e) {

                    }
                }

            }
        }).start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isRunning = false;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
