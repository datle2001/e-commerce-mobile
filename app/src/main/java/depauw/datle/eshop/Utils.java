package depauw.datle.eshop;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;
import java.util.Map;

import depauw.datle.eshop.ui.mainActivity.MainActivity;

public class Utils {
    private static int sTheme;
    public static Map<Integer,Integer> radioButtonId_theme = new HashMap<>();
    public static void changeToTheme(Activity activity, int theme, int radioButtonId)
    {
        sTheme = theme;
        activity.finish();
        Intent intent = new Intent(activity, activity.getClass());
        intent.putExtra(activity.getString(R.string.radioButton_intent_pref), radioButtonId);
        activity.startActivity(intent);
    }
    /** Set the theme of the activity, according to the configuration. */
    public static void onActivityCreateSetTheme(Activity activity)
    {
        radioButtonId_theme.put(R.id.radiobutton_dreamy, R.style.Theme_Dreamy);
        radioButtonId_theme.put(R.id.radiobutton_forest, R.style.Theme_Forest);
        radioButtonId_theme.put(R.id.radiobutton_wealth, R.style.Theme_Wealth);

        if(sTheme > 0) {
            activity.setTheme(sTheme);
        }
    }

    public static void confirmTheme(Activity activity) {
        SharedPreferences.Editor editor = activity
                .getApplication()
                .getSharedPreferences(activity.getString(R.string.shared_pref), Context.MODE_PRIVATE)
                .edit();

        editor.putInt(activity.getString(R.string.theme_pref), sTheme);
        editor.apply();

        activity.finish();
        Intent intent = new Intent(activity, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.startActivity(intent);
    }

    public static void reset() {
        sTheme = 0;
    }
}
