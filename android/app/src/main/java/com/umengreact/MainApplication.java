package com.umengreact;

import android.app.Application;
import android.util.Log;

import com.facebook.react.ReactApplication;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.shell.MainReactPackage;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.soexample.BuildConfig;
//import com.umengreact.BuildConfig;

import java.util.Arrays;
import java.util.List;

public class MainApplication extends Application implements ReactApplication {

  private final ReactNativeHost mReactNativeHost = new ReactNativeHost(this) {
    @Override
    protected boolean getUseDeveloperSupport() {
      return BuildConfig.DEBUG;
    }

    @Override
    protected List<ReactPackage> getPackages() {
      return Arrays.<ReactPackage>asList(
              new SharePackage(),new MainReactPackage()
      );
    }
  };

  @Override
  public ReactNativeHost getReactNativeHost() {
      return mReactNativeHost;
  }
  @Override
  public void onCreate() {
    super.onCreate();
    Config.shareType = "react native";
    UMShareAPI.get(this);

  }

  //各个平台的配置，建议放在全局Application或者程序入口
  {
    //微信 wx12342956d1cab4f9,a5ae111de7d9ea137e88a5e02c07c94d
    PlatformConfig.setWeixin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");
    //豆瓣RENREN平台目前只能在服务器端配置
    //新浪微博
    PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad");
    //易信
    PlatformConfig.setYixin("yxc0614e80c9304c11b0391514d09f13bf");
    PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
    PlatformConfig.setTwitter("3aIN7fuF685MuZ7jtXkQxalyi", "MK6FEYG63eWcpDFgRYw4w9puJhzDl0tyuqWjZ3M7XJuuG7mMbO");
    PlatformConfig.setAlipay("2015111700822536");
    PlatformConfig.setLaiwang("laiwangd497e70d4", "d497e70d4c3e4efeab1381476bac4c5e");
    PlatformConfig.setPinterest("1439206");

  }
}
