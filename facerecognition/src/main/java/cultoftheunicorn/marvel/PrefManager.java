package cultoftheunicorn.marvel;

import android.content.Context;
import android.content.SharedPreferences;


public class PrefManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "microscope";
    private static final String isLogin = "isLogin";
    private static final String userId = "userId";
    private static final String imei = "imei";
    private static final String name = "name";
    public static final String userPin = "user_pin";

    private static final String mac = "mac";

    private static final String x1 = "x1";
    private static final String x2 = "x2";
    private static final String y2 = "y2";
    private static final String y1 = "y1";

    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void clearPreference() {
        editor.clear();
        editor.commit();
    }

    public boolean checkSharedPrefs(String key) {
        if (pref.contains(key)) {
            return true;
        }
        return false;
    }

    public void setUserPin(String value) {
        editor.putString(userPin, value);
        editor.commit();
    }
    public String getUserPin() {
        return pref.getString(userPin, "");
    }

    public void setUserId(String value) {
        editor.putString(userId, value);
        editor.commit();
    }
    public String getUserId() {
        return pref.getString(userId, "");
    }

    public String getMac() {
        return pref.getString(mac, "");
    }

    public void setMac(String value) {
        editor.putString(mac, value);
        editor.commit();
    }

    public String getImei() {
        return pref.getString(imei, "");
    }
    public void setImei(String value) {
        editor.putString(imei, value);
        editor.commit();
    }

    public String getName() {
        return pref.getString(name, "");
    }
    public void setName(String value) {
        editor.putString(name, value);
        editor.commit();
    }

    public void setIsLogin(boolean value) {
        editor.putBoolean(isLogin, value);
        editor.commit();
    }
    public boolean getIsLogin() {
        return pref.getBoolean(isLogin, false);
    }

    public void setInt(String key, int val) {
        editor.putInt(key, val);
        editor.commit();
    }
    public int getInt(String key) {
        return pref.getInt(key, 0);
    }

}