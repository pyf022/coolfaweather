package android.coolfaweather.com.coolfawealther.util;

import android.util.Log;

/**
 * Created by admin on 2017/5/1.
 * 定义日志级别，当level =VERBOSE时候，全部日志打印出来
 * 当level=NOTHING时，所有日志不打印出来，方便项目管理
 */

public class LogUtil {
    public static final int VERBOSE=1;
    public static final int DEBUG=2;
    public static final int INFO=3;
    public static final int WARN=4;
    public static final int ERROR=5;
    public static final int NOTHING=6;
    public static int level=ERROR;//当前日志级别下所有日志均打印出来

    public static void v(String tag,String msg){
        if(level<=VERBOSE){
            Log.v(tag,msg);
        }
    }

    public static void d(String tag,String msg){
        if(level<=DEBUG){
            Log.d(tag,msg);
        }
    }
    public static void i(String tag,String msg){
        if(level<=INFO){
            Log.d(tag,msg);
        }
    }

    public static void w(String tag,String msg){
        if(level<=WARN){
            Log.w(tag,msg);
        }
    }
    public static void e(String tag,String msg){
        if(level<=ERROR){
            Log.e(tag,msg);
        }
    }
}
