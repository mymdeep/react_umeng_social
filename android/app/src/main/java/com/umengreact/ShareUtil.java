package com.umengreact;

import android.app.Activity;
import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.umeng.socialize.Config;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.utils.Log;

import java.util.Map;

/**
 * Created by wangfei on 16/9/26.
 */
public class ShareUtil extends ReactContextBaseJavaModule {
   private static Activity ma;
    private static Handler mSDKHandler = new Handler(Looper.getMainLooper());
    private ReactApplicationContext contect;
    public ShareUtil(ReactApplicationContext reactContext) {
        super(reactContext);
        contect = reactContext;

    }
    public static void initSocialSDK(Activity activity){
       ma = activity;
    }
    @Override
    public String getName() {
        return "shareutil";
    }
    private static void runOnMainThread(Runnable runnable) {
        mSDKHandler.postDelayed(runnable, 0);
    }
    @ReactMethod
    public void share(final String text, final String img, int sharemedia, final Callback successCallback){
        runOnMainThread(new Runnable() {
            @Override
            public void run() {
                new ShareAction(ma).withText(text)
               .withMedia(new UMImage(contect, img))
               .setPlatform(SHARE_MEDIA.QQ)
               .setCallback(new UMShareListener() {
                    @Override
                    public void onResult(SHARE_MEDIA share_media) {
                        Log.e("xxxxx = sssssss");
                        successCallback.invoke(0, "success");
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                        Log.e("xxxxx = eeeeeee");
                        successCallback.invoke(1, throwable.getMessage());
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {
                        Log.e("xxxxx = ccccccc");
                        successCallback.invoke(2, "cancel");
                    }
                })
                .share();
            }
        });

    }
    @ReactMethod
    public void auth(final Callback successCallback){
        runOnMainThread(new Runnable() {
            @Override
            public void run() {
             UMShareAPI.get(ma).doOauthVerify(ma, SHARE_MEDIA.QQ, new UMAuthListener() {
                 @Override
                 public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                     successCallback.invoke("success");
                 }

                 @Override
                 public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                     successCallback.invoke("error");
                 }

                 @Override
                 public void onCancel(SHARE_MEDIA share_media, int i) {
                     successCallback.invoke("cancel");
                 }
             });
            }
        });

    }
    @ReactMethod
    public void get(final Callback successCallback){
        runOnMainThread(new Runnable() {
            @Override
            public void run() {
                UMShareAPI.get(ma).getPlatformInfo(ma, SHARE_MEDIA.QQ, new UMAuthListener() {
                    @Override
                    public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                        successCallback.invoke("success");
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                        successCallback.invoke("error");
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media, int i) {
                        successCallback.invoke("cancel");
                    }
                });
            }
        });

    }
}
