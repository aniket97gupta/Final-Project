package cultoftheunicorn.marvel;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import org.opencv.cultoftheunicorn.marvel.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (Build.VERSION.SDK_INT >= 23) {
            getAllPermissions();
        } else {
            Intent intent = new Intent(getApplicationContext(), BluetoothActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void getAllPermissions() {
        String[] PERMISSIONS = new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE, Manifest.permission.ACCESS_NETWORK_STATE,Manifest.permission.BLUETOOTH,
                Manifest.permission.BLUETOOTH_ADMIN};
        ActivityCompat.requestPermissions(this, PERMISSIONS, 10);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 10:
                if (hasAllPermissionsGranted(grantResults)) {
                    Intent intent = new Intent(getApplicationContext(), BluetoothActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(getApplicationContext(), BluetoothActivity.class);
                    startActivity(intent);
                    finish();
                    //Toast.makeText(getApplicationContext(), "else", Toast.LENGTH_LONG).show();
                    // Permission Denied
                    //ResourceElements.showDialogOk(SplashActivity.this, "Alert", "You have to accept all permission to use applicaiton");
                    //getAllPermissions();
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public boolean hasAllPermissionsGranted(int[] grantResults) {
        for (int grantResult : grantResults) {
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }
}
