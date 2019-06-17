package cultoftheunicorn.marvel;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import org.opencv.cultoftheunicorn.marvel.R;

public class BluetoothActivity extends AppCompatActivity {

    static BluetoothCommands obj;
    BluetoothAdapter mBtAdapter;

    PrefManager prefManager;

    ListView listView;
    ArrayList<Item> arrayList;

    EditText etMacAddr;
    ProgressDialog progressDialog;

    Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);

        prefManager = new PrefManager(this);

        listView = (ListView) findViewById(R.id.lvBluetooths);
        etMacAddr = (EditText) findViewById(R.id.etMacAddr);
        arrayList = new ArrayList<>();

        //etMacAddr.setText("98:D3:31:F5:B4:C2");
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        enableBluetooth();

//        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
//            // BLE is supported, we can use BLE API
//            Toast.makeText(getApplicationContext(), "Supported", Toast.LENGTH_LONG).show();
//        } else {
//            // BLE is not supported, Don’t use BLE capabilities here
//            Toast.makeText(getApplicationContext(), "Not Supported", Toast.LENGTH_LONG).show();
//        }
//
//        final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//        //mBluetoothAdapter.startDiscovery();
//        mBluetoothAdapter.startLeScan(mLeScanCallback);

//        final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
//        mBluetoothAdapter.startDiscovery();
//
//        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
//        registerReceiver(mReceiver, filter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getApplicationContext(), arrayList.get(i), Toast.LENGTH_LONG).show();
                Item item = arrayList.get(i);
                String macAddress = item.getMacAddr();
                prefManager.setMac(macAddress);
                BluetoothCommands.deviceMac = macAddress;
                //Toast.makeText(getApplicationContext(), macAddress, Toast.LENGTH_LONG).show();

                progressDialog = ProgressDialog.show(BluetoothActivity.this, "Connecting.", "Please Wait..", true);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Looper.prepare();
                        obj = new BluetoothCommands(getApplicationContext());
                        obj.connect();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressDialog.dismiss();
                                Toast.makeText(BluetoothActivity.this, "Connected Successfully", Toast.LENGTH_LONG).show();
                                //Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });

//                        new Handler().post(new Runnable() {
//                            @Override
//                            public void run() {
//                                progressDialog.dismiss();
//                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                                startActivity(intent);
//                                finish();
//                            }
//                        });

                    }
                }).start();



//                progressDialog = ProgressDialog.show(BluetoothActivity.this, "Connecting.", "Please Wait..", true);
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        progressDialog.dismiss();
//                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                        startActivity(intent);
//                        finish();
//                    }
//                }, 5000);




            }
        });
    }

    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
            // BLE device was found, we can get its information now
            Toast.makeText(getApplicationContext(), device.getName() + "called", Toast.LENGTH_LONG).show();
            Log.i("veer", "BLE device found: " + device.getName() + "; MAC " + device.getAddress());
        }
    };
// This callback is added to the start scan method as a parameter in this way
// bleAdapter.startLeScan(mLeScanCallback);

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            Log.d("veer", "20");
            unregisterReceiver(mReceiver);
            Log.d("veer", "21");

        } catch(Exception e) {
            Log.d("veer", "22"+e.getMessage());
            Toast.makeText(getApplicationContext(), "Unable to unregister receiver", Toast.LENGTH_LONG).show();
        }

    }

    public void go(View v) {
        String mac = etMacAddr.getText().toString().trim();
        if(mac.length()>0) {
            prefManager.setMac(mac);
            BluetoothCommands.deviceMac = mac;

            new Thread(new Runnable() {
                @Override
                public void run() {
                    Looper.prepare();
                    obj = new BluetoothCommands(getApplicationContext());
                    obj.connect();

//                    new Handler().post(new Runnable() {
//                        @Override
//                        public void run() {
//                            progressDialog.dismiss();
//                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                            startActivity(intent);
//                            finish();
//                        }
//                    });

                }
            }).start();

//            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//            startActivity(intent);
//            finish();

//            progressDialog = ProgressDialog.show(BluetoothActivity.this, "Connecting.", "Please Wait..", true);
//            new Handler().postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    progressDialog.dismiss();
//                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                    startActivity(intent);
//                    finish();
//                }
//            }, 5000);



        } else {
            etMacAddr.setError("Invalid value");
        }
    }

    public void enableBluetooth() {
        // Check device has Bluetooth and that it is turned on
        mBtAdapter= BluetoothAdapter.getDefaultAdapter(); // CHECK THIS OUT THAT IT WORKS!!!
        if(mBtAdapter==null) {
            Toast.makeText(getApplicationContext(), "Device does not support Bluetooth", Toast.LENGTH_SHORT).show();
        } else {
            if (mBtAdapter.isEnabled()) {
                //Toast.makeText(getBaseContext(), "Bluetooth on, scanning", Toast.LENGTH_SHORT).show();
                //obj = new BluetoothCommands(getApplicationContext());
                bluetoothScanning();
            } else {
                //Prompt user to turn on Bluetooth
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, 1);
            }
        }
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(getApplicationContext(), "Scanning, Please wait..", Toast.LENGTH_LONG).show();
                bluetoothScanning();
            } else {
                Toast.makeText(getApplicationContext(), "Retrying", Toast.LENGTH_LONG).show();
                enableBluetooth();
            }
        }

    }

    // Create a BroadcastReceiver for ACTION_FOUND.
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            //Log.d("veer", "16");
            String action = intent.getAction();
            //Toast.makeText(getApplicationContext(), "Veer Test", Toast.LENGTH_LONG).show();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                //Log.d("veer", "17");
                // Discovery has found a device. Get the BluetoothDevice
                // object and its info from the Intent.
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String deviceName = device.getName() + "";
                String deviceHardwareAddress = device.getAddress() + ""; // MAC address

                //Toast.makeText(getApplicationContext(), deviceName + "", Toast.LENGTH_LONG).show();

                String str = deviceName + "\n" + deviceHardwareAddress;
                Item obj = new Item();
                obj.setName(deviceName);
                obj.setMacAddr(deviceHardwareAddress);

                if(arrayList.size()==0)
                    arrayList.add(obj);

                for(int i=0;i<arrayList.size();i++) {
                    String name = arrayList.get(i).getName();
                    if(!name.trim().equalsIgnoreCase(deviceName.trim())) {
                        arrayList.add(obj);
                    }
                }
                //ArrayAdapter adapter =new ArrayAdapter<String>(getApplicationContext(), R.layout.list_item, arrayList);
                ItemAdapter adapter = new ItemAdapter(getApplicationContext(), arrayList);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                progressDialog.dismiss();
            } else if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                Log.d("veer", "ACTION_STATE_CHANGED");
                int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
                switch (state) {
                    case BluetoothAdapter.STATE_OFF:
                        Toast.makeText(getApplicationContext(), "Bluetooth turned off", Toast.LENGTH_LONG).show();
                        enableBluetooth();
                        break;
                    case BluetoothAdapter.STATE_CONNECTING:
                        progressDialog = ProgressDialog.show(BluetoothActivity.this, "Connecting..", "Please Wait..", true);
                        break;
                    case BluetoothAdapter.STATE_CONNECTED:
                        progressDialog.dismiss();
                        Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent1);
                        finish();
                        break;
                }
            }
        }
    };

    void bluetoothScanning() {
        progressDialog = ProgressDialog.show(BluetoothActivity.this, "Searching..", "Please Wait..", true);
        try {
            IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
            filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
            filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
            this.registerReceiver(mReceiver, filter);

//            IntentFilter filter1 = new IntentFilter(BluetoothDevice.ACTION_ACL_CONNECTED);
//            this.registerReceiver(mReceiver, filter1);

            final BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            mBluetoothAdapter.startDiscovery();
        } catch(Exception e) {
            Log.d("veer", "Error : " + e.getMessage());
            Toast.makeText(getApplicationContext(), "Unable to register receiver", Toast.LENGTH_LONG).show();
        }
    }

    class Item {
        String name, macAddr;

        public String getName() {
            return name;
        }

        public String getMacAddr() {
            return macAddr;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setMacAddr(String macAddr) {
            this.macAddr = macAddr;
        }
    }

    class ItemAdapter extends BaseAdapter {
        Context context;
        private LayoutInflater mInflater;
        private ArrayList<Item> arrayList;// = new ArrayList<Place>();

        public ItemAdapter(Context context, ArrayList<Item> arrayList) {
            this.context = context;
            this.arrayList = arrayList;
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
            final ViewHolder holder;
            final Item group = arrayList.get(position);
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.list_item, null);
                holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
                holder.tvMacAddr = (TextView) convertView.findViewById(R.id.tvMacAddr);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tvName.setText(group.getName());
            holder.tvMacAddr.setText(group.getMacAddr());
            return convertView;
        }
    }

    class ViewHolder {
        TextView tvName, tvMacAddr;
    }
}
