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
    public void share(final String text, final String img, final String weburl, final String title, final int sharemedia, final Callback successCallback){
        runOnMainThread(new Runnable() {
            @Override
            public void run() {
                new ShareAction(ma).withText(text)
               .withMedia(new UMImage(contect, img))
                .withTargetUrl(weburl)
                        .withTitle(title)
                        .setPlatform(getShareMedia(sharemedia))
               .setCallback(new UMShareListener() {
                    @Override
                    public void onResult(SHARE_MEDIA share_media) {
                        successCallback.invoke(0, "success");
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                        successCallback.invoke(1, throwable.getMessage());
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {
                        successCallback.invoke(2, "cancel");
                    }
                })
                .share();
            }
        });

    }
    @ReactMethod
    public void auth(final int  sharemedia, final Callback successCallback){
        runOnMainThread(new Runnable() {
            @Override
            public void run() {
             UMShareAPI.get(ma).doOauthVerify(ma, getShareMedia(sharemedia), new UMAuthListener() {
                 @Override
                 public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                     successCallback.invoke(0,"success");
                 }

                 @Override
                 public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                     successCallback.invoke(1,"error");
                 }

                 @Override
                 public void onCancel(SHARE_MEDIA share_media, int i) {
                     successCallback.invoke(2,"cancel");
                 }
             });
            }
        });

    }
    @ReactMethod
    public void get(final int  sharemedia,final Callback successCallback){
        runOnMainThread(new Runnable() {
            @Override
            public void run() {
                UMShareAPI.get(ma).getPlatformInfo(ma,getShareMedia(sharemedia), new UMAuthListener() {
                    @Override
                    public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                        successCallback.invoke(0,"success");
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                        successCallback.invoke(1,"error");
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media, int i) {
                        successCallback.invoke(2,"cancel");
                    }
                });
            }
        });

    }
    @ReactMethod
    public void shareboard(final String text, final String img, final String weburl, final String title, final Callback successCallback){
        runOnMainThread(new Runnable() {
            @Override
            public void run() {
               new ShareAction(ma).setDisplayList(SHARE_MEDIA.QQ,SHARE_MEDIA.SINA,SHARE_MEDIA.WEIXIN)
                       .withTargetUrl(weburl)
                       .withText(text)
                       .withTitle(title)
                       .withMedia(new UMImage(ma,img))
                       .setCallback(new UMShareListener() {
                           @Override
                           public void onResult(SHARE_MEDIA share_media) {
                               successCallback.invoke(0, "success");
                           }

                           @Override
                           public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                               successCallback.invoke(1, throwable.getMessage());
                           }

                           @Override
                           public void onCancel(SHARE_MEDIA share_media) {
                               successCallback.invoke(2, "cancel");
                           }
                       } )
                       .open();
            }
        });

    }
    private SHARE_MEDIA getShareMedia(int num){
        switch (num){
            case 0:
                return SHARE_MEDIA.QQ;

            case 1:
                return SHARE_MEDIA.SINA;

            case 2:
                return SHARE_MEDIA.WEIXIN;

            case 3:
                return SHARE_MEDIA.WEIXIN_CIRCLE;

            default:
                return SHARE_MEDIA.QQ;
        }
    }
}
