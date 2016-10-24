//
//  RNIOSAlert.m
//  UMENGREACT
//
//  Created by wangfei on 16/9/29.
//  Copyright © 2016年 Facebook. All rights reserved.
//

#import "RNIOSAlert.h"
#import <UMSocialCore/UMSocialCore.h>
#import "UMSocialUIManager.h"
@implementation RNIOSAlert
RCT_EXPORT_MODULE();
RCT_EXPORT_METHOD(show:(NSString *)text image:(NSString *)image url:(NSString *)url title:(NSString*)title callback:(RCTResponseSenderBlock)callback){
  UMSocialMessageObject *messageObject = [UMSocialMessageObject messageObject];
  messageObject.text = text;
  UMShareWebpageObject* webobj = [UMShareWebpageObject shareObjectWithTitle:title descr:text thumImage:image];
  webobj.webpageUrl = url;
  messageObject.shareObject = webobj;
  dispatch_sync(dispatch_get_main_queue(), ^{
    
    //主线程更新
    [[UMSocialManager defaultManager] shareToPlatform:UMSocialPlatformType_QQ messageObject:messageObject currentViewController:self completion:^(id data, NSError *error) {

      
      NSString *message = nil;
      if (!error) {
        message = [NSString stringWithFormat:@"分享成功"];
       
      }
      else{
        if (error) {
          message = [NSString stringWithFormat:@"失败原因Code: %d\n",(int)error.code];
        }
        else{
          message = [NSString stringWithFormat:@"分享失败"];
        }
      }
    
       callback( [[NSArray alloc] initWithObjects:message, nil]);
    }];
  });
  
 
}
RCT_EXPORT_METHOD(auth:(RCTResponseSenderBlock)callback){
 [[UMSocialManager defaultManager] authWithPlatform:UMSocialPlatformType_WechatSession currentViewController:nil completion:^(id result, NSError *error) {
    UMSocialAuthResponse *authresponse = result;
     NSString *message = [NSString stringWithFormat:@"result: %d\n uid: %@\n accessToken: %@\n",(int)error.code,authresponse.uid,authresponse.accessToken];
    callback( [[NSArray alloc] initWithObjects:message, nil]);
 }];
}
RCT_EXPORT_METHOD(getinfo:(RCTResponseSenderBlock)callback){
  [[UMSocialManager defaultManager] getUserInfoWithPlatform:UMSocialPlatformType_WechatSession currentViewController:nil completion:^(id result, NSError *error) {
    UMSocialUserInfoResponse *userinfo =result;
    NSString *message = [NSString stringWithFormat:@"name: %@\n icon: %@\n gender: %@\n",userinfo.name,userinfo.iconurl,userinfo.gender];
     callback( [[NSArray alloc] initWithObjects:message, nil]);
   
    
  }];
}
RCT_EXPORT_METHOD(shareboard:(NSString *)text image:(NSString *)image url:(NSString *)url title:(NSString*)title callback:(RCTResponseSenderBlock)callback){
  UMSocialMessageObject *messageObject = [UMSocialMessageObject messageObject];
  messageObject.text = text;
  UMShareWebpageObject* webobj = [UMShareWebpageObject shareObjectWithTitle:title descr:text thumImage:image];
  webobj.webpageUrl = url;
  messageObject.shareObject = webobj;
  dispatch_sync(dispatch_get_main_queue(), ^{
    __weak typeof(self) weakSelf = self;
    //显示分享面板
    [UMSocialUIManager showShareMenuViewInWindowWithPlatformSelectionBlock:^(UMShareMenuSelectionView *shareSelectionView, UMSocialPlatformType platformType) {
      [[UMSocialManager defaultManager] shareToPlatform:platformType messageObject:messageObject currentViewController:self completion:^(id data, NSError *error) {
        
        
        NSString *message = nil;
        if (!error) {
          message = [NSString stringWithFormat:@"分享成功"];
          
        }
        else{
          if (error) {
            message = [NSString stringWithFormat:@"失败原因Code: %d\n",(int)error.code];
          }
          else{
            message = [NSString stringWithFormat:@"分享失败"];
          }
        }
        
        callback( [[NSArray alloc] initWithObjects:message, nil]);
      }];
    }];
   
  });
}
@end
