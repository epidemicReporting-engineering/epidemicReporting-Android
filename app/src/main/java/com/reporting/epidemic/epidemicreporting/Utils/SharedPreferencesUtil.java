package com.reporting.epidemic.epidemicreporting.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Map;

public class SharedPreferencesUtil {

    public static final String mTAG = "dev";
    private static SharedPreferences mPreferences;
    private static SharedPreferences.Editor mEditor;
    private static SharedPreferencesUtil mSharedPreferencesUtil;

    public SharedPreferencesUtil(Context context) {
        mPreferences =   context.getSharedPreferences(mTAG,Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();
    }

    public synchronized  static SharedPreferencesUtil getInstance(Context context) {
        if (mSharedPreferencesUtil == null){
            mSharedPreferencesUtil = new SharedPreferencesUtil(context);
        }
        return  mSharedPreferencesUtil;
    }

    /**
     * 存储
     */
    public void put(String key, Object object) {
        if (object instanceof String) {
            mEditor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            mEditor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            mEditor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            mEditor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            mEditor.putLong(key, (Long) object);
        } else {
            mEditor.putString(key, object.toString());
        }
        mEditor.commit();
    }

    /**
     * 获取保存的数据
     */
    public Object getSharedPreference(String key, Object defaultObject) {
        if (defaultObject instanceof String) {
            return mPreferences.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return mPreferences.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return mPreferences.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return mPreferences.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return mPreferences.getLong(key, (Long) defaultObject);
        } else {
            return mPreferences.getString(key, null);
        }
    }

    /**
     * 移除某个key值已经对应的值
     */
    public void remove(String key) {
        mEditor.remove(key);
        mEditor.commit();
    }

    /**
     * 清除所有数据
     */
    public void clear() {
        mEditor.clear();
        mEditor.commit();
    }

    /**
     * 查询某个key是否存在
     */
    public Boolean contain(String key) {
        return mPreferences.contains(key);
    }

    /**
     * 返回所有的键值对
     */
    public Map<String, ?> getAll() {
        return mPreferences.getAll();
    }
}