package com.mtj.common.util;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ColorUtils;
import com.mtj.common.R;
import com.mtj.common.pop.CallMobilePop;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneUtil {
    //判断是否为手机号
   public static String regex = "\\d{7,18}|\\d{3,4}-\\d{7,18}|\\d{5,6}-\\d{3,6}|(\\d{3,6}-){2,3}\\d{3,6}";


}
