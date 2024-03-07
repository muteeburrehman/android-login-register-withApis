package pk.codebase.loginregisterwithrestapis;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreference extends Application {


    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        System.out.println("--------Malok ha------");
        context = getApplicationContext();
    }


    public static SharedPreferences getPreferenceManager() {
        return context.getSharedPreferences("shared_prefs", MODE_PRIVATE);
    }


    public static void saveStringToSharedPreferences(String key, String value) {
        SharedPreferences sharedPreferences = getPreferenceManager();
        sharedPreferences.edit().putString(key, value).apply();
    }

    public static String getStringFromSharedPreferences(String key) {
        SharedPreferences sharedPreferences = getPreferenceManager();
        return sharedPreferences.getString(key, "");
    }
}
