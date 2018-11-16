package com.cs380.csun.findafriend;

import android.bluetooth.BluetoothAdapter;
import java.io.IOException;
import java.util.UUID;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.util.Log;

//bluetooth class created by Greg/Sam

public class BluetoothConnection {
    private static final String TAG = "BluetoothConnection";
    private static final String appName = "Find-A-Friend";
    private static final UUID MY_UUID_INSECURE = UUID.fromString("8ce2550-200a-11e0-ac64-0800200c9a66");
    private final BluetoothAdapter mBluetoothAdapter;
    Context mContext;
    private AcceptThread mInsecureAcceptThread;
    public BluetoothConnection(Context context) {
        mContext = context;
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    }
    private class AcceptThread extends Thread {
        private final BluetoothServerSocket  mmServerSocket;
        public AcceptThread() {
            BluetoothServerSocket tmp = null;
            //create a new listening socket
            try {
                tmp = mBluetoothAdapter.listenUsingInsecureRfcommWithServiceRecord(appName, MY_UUID_INSECURE);
                Log.d(TAG, "Accept Thread: Setting up Server Using:" + MY_UUID_INSECURE);
            } catch (IOException e) {
                Log.e(TAG, "Accept Thread: IOException: " + e.getMessage());
            }
            mmServerSocket = tmp;
        }
        public void run() {
            Log.d(TAG, "run: AcceptThread Running. ");
            BluetoothSocket socket = null;
            try {
                //this is a blocking call and only returns successful connection or exception
                Log.d(TAG, "run: RFCOM server socket start.......");
                socket = mmServerSocket.accept();
                Log.d(TAG, "run: RFCOM server socket accepted connection");
            } catch (IOException e) {
                Log.e(TAG, "Accept Thread: IOException: " + e.getMessage());
            }
            if(socket != null){
                //connected(socket, mmDevice);
            }
            Log.i(TAG, "END mAcceptThread");
        }
        public void cancel(){
            Log.d(TAG, "cancel: Canceling AcceptThread");
            try{
                mmServerSocket.close();
            }catch(IOException e){
                Log.e(TAG, "cancel: Close of AcceptedThread ServerSocket failed. " + e.getMessage());
            }
        }
    }
}

