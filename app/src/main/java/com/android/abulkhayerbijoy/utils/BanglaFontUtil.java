package com.android.abulkhayerbijoy.utils;

import android.app.Activity;
import android.text.TextUtils;

import com.android.abulkhayerbijoy.startup.Startup;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.text.Normalizer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BanglaFontUtil {

    Map<String, String> ar;
    Activity _activity = null;
    JSONObject jObject = null;
    List<String> Months = new ArrayList<String>();
    List<String> BanglaMonths = new ArrayList<String>();
    List<String> uMonths = new ArrayList<String>();
    String fb = "0123456789ABCDEF";

    public BanglaFontUtil() {

        try {
            ar = jsonToMap(loadJSONFromAsset2("ar.json"));
            loadMonths();
            loadUnicodeMonths();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String Replace(String input, String pattern, String replacement) {

        String retStr = "";
        //String inputText = Normalizer.normalize(input, Normalizer.Form.NFD);
        String REGEX = pattern;
        Pattern p = Pattern.compile(REGEX);
        Matcher m = p.matcher(input);
        retStr = m.replaceAll(replacement);


        return retStr;
    }

    private void loadMonths() {
        /*Dont't Change Here*/
        Months.add("Rvbyqvix");
        Months.add("‡de«yqvix");
        Months.add("gvP©");
        Months.add("Gwc«j");
        Months.add("‡g");
        Months.add("Ryb");
        Months.add("RyjvB");
        Months.add("AMv÷");
        Months.add("‡m‡Þ¤^i");
        Months.add("A‡±vei");
        Months.add("b‡f¤^i");
        Months.add("wW‡m¤^i");
    }

    private void loadUnicodeMonths() {
        /*Dont't Change Here*/
        uMonths.add("জানুয়ারী");
        uMonths.add("ফেব্রুয়ারী");
        uMonths.add("মার্চ");
        uMonths.add("এপ্রিল");
        uMonths.add("মে");
        uMonths.add("জুন");
        uMonths.add("জুলাই");
        uMonths.add("অগাস্ট");
        uMonths.add("সেপ্টেম্বর");
        uMonths.add("অক্টোবর");
        uMonths.add("নভেম্বর");
        uMonths.add("ডিসেম্বর");
    }

    public String NumberToBangla(String number) {

        String numBan = "";

        for (int i = 0; i < number.length(); i++) {

            numBan += getUnicodedNum(number.charAt(i));
        }

        return numBan;
    }

    private String getUnicodedNum(char number) {

        if (number == '1') {
            return "১";
        } else if (number == '2') {
            return "২";
        } else if (number == '3') {
            return "৩";
        } else if (number == '4') {
            return "৪";
        } else if (number == '5') {
            return "৫";
        } else if (number == '6') {
            return "৬";
        } else if (number == '7') {
            return "৭";
        } else if (number == '8') {
            return "৮";
        } else if (number == '9') {
            return "৯";
        } else if (number == '0') {
            return "০";
        }
        else if (number == '.') {
            return ".";
        }
        return "";
    }

    public String BanglaToEnglish(String number) {

        String numBan = "";

        for (int i = 0; i < number.length(); i++) {

            numBan += getUnicodedNumInBangla(number.charAt(i));
        }

        return numBan;
    }

    private String getUnicodedNumInBangla(char number) {

        if (number == '১') {
            return "1";
        } else if (number == '২') {
            return "2";
        } else if (number == '৩') {
            return "3";
        } else if (number == '৪') {
            return "4";
        } else if (number == '৫') {
            return "5";
        } else if (number == '৬') {
            return "6";
        } else if (number == '৭') {
            return "7";
        } else if (number == '৮') {
            return "8";
        } else if (number == '৯') {
            return "9";
        } else if (number == '০') {
            return "0";
        }else if (number == '.') {
            return ".";
        }
        return "";
    }

    public String dateInBanglaUnicode(String pDate, String dateFormat) {
        String banglaDate = "";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date d = null;

        try {
            d = sdf.parse(pDate);
            Calendar cal = Calendar.getInstance();
            cal.setTime(d);
            int month = cal.get(Calendar.MONTH);
            int date = cal.get(Calendar.DATE);
            int yr = cal.get(Calendar.YEAR);

            banglaDate = NumberToBangla(String.valueOf(date)) + " " + uMonths.get(month) + " " + NumberToBangla(String.valueOf(yr));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return banglaDate;
    }

    public String dateInBangla(String pDate, String dateFormate) {
        String banglaDate = "";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormate);
        Date d = new Date();

        try {
            d = sdf.parse(pDate);
            Calendar cal = Calendar.getInstance();
            cal.setTime(d);
            int month = cal.get(Calendar.MONTH);
            int date = cal.get(Calendar.DATE);
            int yr = cal.get(Calendar.YEAR);

            banglaDate = String.valueOf(date) + " " + Months.get(month) + " " + String.valueOf(yr).substring(2);


        } catch (ParseException e) {
            e.printStackTrace();
        }

        return banglaDate;
    }

    public String loadJSONFromAsset2(String name) throws IOException {
        InputStream is = Startup.getContext().getAssets().open(name);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8"))); //StandardCharsets.UTF_8
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } finally {
            is.close();
        }

        String jsonString = writer.toString();

        return jsonString;

    }

    public Map<String, String> jsonToMap(String t) {

        Map<String, String> map = new LinkedHashMap<>();


        try {
            jObject = new JSONObject(t);
            Iterator<?> keys = jObject.keys();

            while (keys.hasNext()) {
                String key = (String) keys.next();
                //String finalKey = Normalizer.normalize(key, Normalizer.Form.NFD);
                String value = jObject.getString(key);
                map.put(key, value);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return map;
    }

    private String ci(String x) throws UnsupportedEncodingException {
        int cY = 0;

        String w = Normalizer.normalize(x, Normalizer.Form.NFD);
        char[] cr = w.toCharArray();
        for (int i = 0; i < w.length(); i++) {
            if (i < w.length() && ao(w.charAt(i))) {
                int j = 1;
                while (v(w.charAt(i - j))) {
                    if (i - j < 0) break;
                    if (i - j <= cY) break;
                    if (D(w.charAt(i - j - 1))) j += 2;
                    else break;
                }
                String R = w.substring(0, i - j);
                R += w.charAt(i);
                R += w.substring(i - j, i);
                R += w.substring(i + 1, w.length());
                w = R;
                cY = i + 1;
                continue;
            }
            if (i < w.length() - 1 && D(w.charAt(i)) && w.charAt(i - 1) == 'র' && !D(w.charAt(i - 2))) {
                int j = 1;
                int aZ = 0;
                while (true) {
                    if (w.charAt(i - 1) == 'র' && w.charAt(i) == '্') {
                        aZ = 1;
                        j -= 1;
                        break;
                    } else if (v(w.charAt(i + j)) && D(w.charAt(i + j + 1))) j += 2;
                    else if (v(w.charAt(i + j)) && ao(w.charAt(i + j + 1))) {
                        aZ = 1;
                        break;
                    } else break;
                }
                String R = w.substring(0, i - 1);
                R += w.substring(i + j + 1, i + j + aZ + 1);
                R += w.substring(i + 1, i + j + 1);
                R += w.charAt(i - 1);
                R += w.charAt(i);
                R += w.substring(i + j + aZ + 1, w.length());
                w = R;
                i += (j + aZ);
                cY = i + 1;
                continue;
            }
        }
        return w;
    }

    String fa(char d) {
        String h = fb.substring(d & 15, 1);
        while (d > 15) {
            d >>= 4;
            h = fb.substring(d & 15, 1) + h;
        }
        while (h.length() < 4) h = "0" + h;
        return h;
    }

    String bF(String line, boolean ef) {
        String text = "";
        for (int i = 0; i < line.length(); i++) {
            if (eu(line.charAt(i))) text += line.charAt(i);
            else
                text += "&#" + (ef ? String.valueOf(fa(line.charAt(i))) : line.codePointAt(Integer.valueOf(i))) + ";";
        }
        return text;
    }

    public String Convert(String line) {
        if (TextUtils.isEmpty(line))
            return "";

        Map<String, String> G = ar;

        line = Replace(line, "ো", "ো");
        line = Replace(line, "ৌ", "ৌ");
        try {
            line = ci(line);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        for (Map.Entry<String, String> entry : G.entrySet()) {
            line = Replace(line, entry.getKey(), entry.getValue());
        }
        return line;
    }

    boolean bA(char e) {
        if (e == '০' || e == '১' || e == '২' || e == '৩' || e == '৪' || e == '৫' || e == '৬' || e == '৭' || e == '৮' || e == '৯')
            return true;
        return false;
    }

    boolean ao(char e) {
        if (e == 'ি' || e == 'ৈ' || e == 'ে')
            return true;

        return false;
    }

    boolean aJ(char e) {
        if (e == 'া' || e == 'ো' || e == 'ৌ' || e == 'ৗ' || e == 'ু' || e == 'ূ' || e == 'ী' || e == 'ৃ')
            return true;
        return false;
    }

    boolean ah(char e) {
        if (ao(e) || aJ(e)) return true;
        return false;
    }

    boolean v(char e) {
        if (e == 'ক' || e == 'খ' || e == 'গ' || e == 'ঘ' || e == 'ঙ' || e == 'চ' || e == 'ছ' || e == 'জ' || e == 'ঝ' || e == 'ঞ' || e == 'ট' || e == 'ঠ' || e == 'ড' || e == 'ঢ' || e == 'ণ' || e == 'ত' || e == 'থ' || e == 'দ' || e == 'ধ' || e == 'ন' || e == 'প' || e == 'ফ' || e == 'ব' || e == 'ভ' || e == 'ম' || e == 'শ' || e == 'ষ' || e == 'স' || e == 'হ' || e == 'য' || e == 'র' || e == 'ল' || e == 'য়' || e == 'ং' || e == 'ঃ' || e == 'ঁ' || e == 'ৎ')
            return true;
        return false;
    }

    boolean Q(char e) {
        if (e == 'অ' || e == 'আ' || e == 'ই' || e == 'ঈ' || e == 'উ' || e == 'ঊ' || e == 'ঋ' || e == 'ঌ' || e == 'এ' || e == 'ঐ' || e == 'ও' || e == 'ঔ')
            return true;
        return false;
    }

    boolean aF(char e) {
        if (e == 'ং' || e == 'ঃ' || e == 'ঁ') return true;
        return false;
    }

    boolean bS(String e) {
        if (e == "্য" || e == "্র") return true;
        return false;
    }

    boolean D(char e) {
        if (e == '্') return true;
        return false;
    }

    boolean eT(char e) {
        if (bA(e) || ah(e) || v(e) || Q(e) || aF(e) || bS(String.valueOf(e)) || D(e)) return true;
        return false;
    }

    boolean eu(char e) {
        if (e >= 0 && e < 128) return true;
        return false;
    }

    boolean cy(String e) {
        if (e == " " || e == "	" || e == "\n" || e == "\r") return true;
        return false;
    }

    String cJ(String e) {
        String t = "";
        if (e == "া") t = "আ";
        else if (e == "ি") t = "ই";
        else if (e == "ী") t = "ঈ";
        else if (e == "ু") t = "উ";
        else if (e == "ূ") t = "ঊ";
        else if (e == "ৃ") t = "ঋ";
        else if (e == "ে") t = "এ";
        else if (e == "ৈ") t = "ঐ";
        else if (e == "ো") t = "ও";
        else if (e == "ো") t = "ও";
        else if (e == "ৌ") t = "ঔ";
        else if (e == "ৌ") t = "ঔ";
        return t;
    }

    String bc(String e) {
        String t = "";
        if (e == "আ") t = "া";
        else if (e == "ই") t = "ি";
        else if (e == "ঈ") t = "ী";
        else if (e == "উ") t = "ু";
        else if (e == "ঊ") t = "ূ";
        else if (e == "ঋ") t = "ৃ";
        else if (e == "এ") t = "ে";
        else if (e == "ঐ") t = "ৈ";
        else if (e == "ও") t = "ো";
        else if (e == "ঔ") t = "ৌ";
        return t;
    }


}
