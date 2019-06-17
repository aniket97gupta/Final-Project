package cultoftheunicorn.marvel;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

public class BluetoothCommands {

    public static String readMessage="", response = "";
    public static String result="0";
    BluetoothAdapter mBtAdapter;
    ArrayAdapter mArrayAdapter;
    BluetoothDevice device;
    BluetoothSocket btsocket;
    private ConnectedThread mConnectedThread;
    final int handlerState=0;
    Handler bluetoothIn;
    //private StringBuilder recDataString=new StringBuilder();
    private static final UUID BTMODULEUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    public static String deviceMac = "98:D3:31:F5:B4:C2";

    final Context context;
    String recDataString = "";

    public BluetoothCommands(final Context context) {
        // TODO Auto-generated constructor stub

        this.context = context;

//        bluetoothstatus();
//        getpaireddevices();

        bluetoothIn=new Handler(){
            public void handleMessage(Message msg){
                Log.d("veer check", "called");
                if(msg.what==handlerState) {
                    readMessage = (String)msg.obj;
                    recDataString = recDataString + readMessage;
                    //Log.d("veer length", recDataString + "  " + recDataString.toString().trim().length() + "");
                } else {
                    Log.d("veer check", "called 000");
                    Toast.makeText(context, "handle state is 0", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    public void bluetoothstatus() {
        // Check device has Bluetooth and that it is turned on
        mBtAdapter= BluetoothAdapter.getDefaultAdapter(); // CHECK THIS OUT THAT IT WORKS!!!
        if(mBtAdapter==null) {
            Toast.makeText(context, "Device does not support Bluetooth", Toast.LENGTH_SHORT).show();
        } else {
            if (mBtAdapter.isEnabled()) {
                //Toast.makeText(getBaseContext(), "Bluetooth ON", Toast.LENGTH_SHORT).show();
            } else {
                //Prompt user to turn on Bluetooth
                //Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                //context.startActivityForResult(enableBtIntent, 1);
            }
        }
    }
    public void write(String cmd) {
        mConnectedThread.write(cmd);
        Toast.makeText(context, "Command : " + cmd, Toast.LENGTH_SHORT).show();
    }

    public boolean connect() {
        boolean flag = false;
        Toast.makeText(context, "searching device ", Toast.LENGTH_LONG).show();
        mBtAdapter= BluetoothAdapter.getDefaultAdapter();

        device=mBtAdapter.getRemoteDevice(deviceMac);
        try {
            btsocket = createBluetoothSocket(device);
        } catch (Exception ex){
            Log.d("veer", ex.getMessage() + "1");
            Toast.makeText(context, "socket connection failed " + ex, Toast.LENGTH_LONG).show();
        }
        //Establish the Bluetooth socket connection.
        try {
            btsocket.connect();
            flag = true;
            Toast.makeText(context, "Connected Successfully", Toast.LENGTH_LONG).show();
        } catch (Exception ex){
            Log.d("veer", ex.getMessage() + "2");
            try{
                Toast.makeText(context, "Socket Closed "
                        + ex.getMessage(), Toast.LENGTH_LONG).show();
                btsocket.close();
            }catch(Exception ex2){
                Log.d("veer", ex.getMessage() + "3");
                Toast.makeText(context, "Exception 2: " + ex, Toast.LENGTH_LONG).show();
            }
        }
        mConnectedThread=new ConnectedThread(btsocket);
        mConnectedThread.start();

        return flag;
    }

    public void disconnect() {
        try{
            btsocket.close();
        }catch(Exception ex2){

        }
    }

    private BluetoothSocket createBluetoothSocket(BluetoothDevice device) throws IOException {
        return device.createRfcommSocketToServiceRecord(BTMODULEUUID);
    }
    public void getpaireddevices()
    {
        Toast.makeText(context, "searching device ", Toast.LENGTH_LONG).show();
        mBtAdapter= BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> pairedDevices =mBtAdapter.getBondedDevices();
        if(pairedDevices.size()>0) {      //create device and set MAC address
            device=mBtAdapter.getRemoteDevice(deviceMac);
            try{
                btsocket = createBluetoothSocket(device);
            }
            catch (Exception ex){
                Toast.makeText(context, "socket connection failed " + ex, Toast.LENGTH_LONG).show();
            }
            //Establish the Bluetooth socket connection.
            try{

                btsocket.connect();
                //Toast.makeText(getBaseContext(),"socket connection done ", Toast.LENGTH_LONG).show();
            }
            catch (Exception ex){
                try{
                    btsocket.close();
                    Toast.makeText(context, "Socket Closed "
                            + ex.getMessage(), Toast.LENGTH_LONG).show();
                }catch(Exception ex2){
                    Toast.makeText(context, "Exception 2: " + ex, Toast.LENGTH_LONG).show();
                }
            }
            mConnectedThread=new ConnectedThread(btsocket);
            mConnectedThread.start();
        }
        else
        {
            Toast.makeText(context, "No Device Paired", Toast.LENGTH_SHORT).show();
        }
    }
    private class ConnectedThread extends Thread {
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;
        //DataInputStream din = null;

        //creation of the connect thread
        public ConnectedThread(BluetoothSocket socket) {
            InputStream tmpIn = null;
            OutputStream tmpOut = null;

            try {
                //Create I/O streams for connection
                tmpIn = socket.getInputStream();
                //din = new DataInputStream(socket.getInputStream());
                tmpOut = socket.getOutputStream();
                //Toast.makeText(getBaseContext(), "Strem Creation Created ", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                Toast.makeText(context, "Strem Creation Failed " + e.getMessage(), Toast.LENGTH_LONG).show();
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;

        }
        public void run() {
            byte[] buffer = new byte[1024];
            int bytes;

            // Keep looping to listen for received messages
            while (true) {
                try {
                    /*Log.d("veer..", "waiting");
                    result = din.readUTF();
                    Log.d("veer..", result);*/

//                    String msg = "";
//                    while(mmInStream.available()>0) {
//                        bytes = mmInStream.read(buffer);
//                        String msg1 = new String(buffer, 0, bytes);
//                        msg = msg + msg1;
//                    }
                    bytes = mmInStream.available();
                    bytes = mmInStream.read(buffer, 0, bytes);


                    //bytes = mmInStream.read(buffer);

                    String msg = new String(buffer, 0, bytes);
                    readMessage = readMessage + msg;
                    Log.d("veer msg", readMessage + ", " + bytes);

                    //bluetoothIn.obtainMessage(handlerState, bytes, -1, buffer).sendToTarget();
                } catch (IOException e) {
                    Log.d("veer", "error");
                    Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_LONG).show();
                    break;
                }
            }
        }
        //write method
        public void write(String input) {
            byte[] msgBuffer = input.getBytes();           //converts entered String into bytes
            try {
                if(mmOutStream!=null)
                    mmOutStream.write(msgBuffer);                //write bytes over BT connection via outstream
                else
                    Toast.makeText(context, "Socket is not opened", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                //if you cannot write, close the application
                Toast.makeText(context, "Connection Failure " + e.getMessage(), Toast.LENGTH_LONG).show();
                //finish();

            }
        }
    }
}
