package com.ezz.bytourism1;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

/**
 * Created by johnchen on 16/4/4.
 */
public class BaseActivity  extends Activity{

    public static final String USER_ID = "user_id";
    public static final String USER_NAME = "user_name";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO
        super.onCreate(savedInstanceState);

    }

    /**
     * 将此次登陆的账户信息存储下来
     * */
    protected void savePreferenceName(String value) {
        // 获取SharedPreference
        SharedPreferences preference = getSharedPreferences("android",
                MODE_PRIVATE);
        // 获取editor
        SharedPreferences.Editor editor = preference.edit();
        // 存入数据
        editor.putString(USER_NAME, value);
        // 提交存入文件中
        editor.commit();
    }
    protected void savePreferenceId(Integer value) {
        // 获取SharedPreference
        SharedPreferences preference = getSharedPreferences("android",
                MODE_PRIVATE);
        // 获取editor
        SharedPreferences.Editor editor = preference.edit();
        // 存入数据
        editor.putInt(USER_ID, value);
        // 提交存入文件中
        editor.commit();
    }
    /**
     * 获取存入SharedPreference中的账户
     *
     * @return
     * */
    protected String getPreferenceName() {
        // 获取SharedPreference
        SharedPreferences preference = getSharedPreferences("android",
                MODE_PRIVATE);
        // 获取存在SharedPreference中的用户名
        return  preference.getString(USER_NAME, "");

    }
    protected Integer getPreferenceId() {
        // 获取SharedPreference
        SharedPreferences preference = getSharedPreferences("android",
                MODE_PRIVATE);
        // 获取存在SharedPreference中的用户名
        return  preference.getInt(USER_ID,0);

    }
    protected void deletePreference() {
        // 获取SharedPreference
        SharedPreferences preference = getSharedPreferences("android",
                MODE_PRIVATE);
        // 获取editor
        SharedPreferences.Editor editor = preference.edit();
        // 存入数据
        editor.putInt(USER_ID,0);
        editor.putString(USER_NAME,"");
        // 提交存入文件中
        editor.commit();
    }
}
