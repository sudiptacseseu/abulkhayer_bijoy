package com.android.abulkhayerbijoy.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Util {

    public static boolean isJSONValid(String test) {
        try {
            new JSONObject(test);
        } catch (JSONException ex) {
            try {
                new JSONArray(test);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }
}
