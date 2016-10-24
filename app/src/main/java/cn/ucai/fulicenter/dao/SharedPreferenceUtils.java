package cn.ucai.fulicenter.dao;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2016/10/24.
 */
public class SharedPreferenceUtils {
    private static final String SHARE_NAME = "savaUserInfo";
    private static SharedPreferenceUtils instance;
    private SharedPreferences mSharedPreference;
    private SharedPreferences.Editor mEditor;
    public static final String SHARE_KEY_USER_NAME = "share_key_user_name";

    public SharedPreferenceUtils(Context context) {
        mSharedPreference = context.getSharedPreferences(SHARE_NAME,Context.MODE_PRIVATE);
        mEditor = mSharedPreference.edit();
    }

    public static SharedPreferenceUtils getInstence(Context context){
        if (instance == null){
            instance = new SharedPreferenceUtils(context);
        }
        return instance;
    }

    public void savaUser(String username){
        mEditor.putString(SHARE_KEY_USER_NAME,username);
        mEditor.commit();
    }

    public String getUser(){
        return mSharedPreference.getString(SHARE_KEY_USER_NAME,null);
    }

    public void removeUser(){
        mEditor.remove(SHARE_KEY_USER_NAME);
        mEditor.commit();
    }
}
