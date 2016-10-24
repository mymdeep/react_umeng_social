/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 * @flow
 */

import React, { Component } from 'react';
import {
  AppRegistry,
  StyleSheet,
  TouchableOpacity,
  NativeModules,
  Text,
  View
} from 'react-native';
var RNIOSAlert = NativeModules.RNIOSAlert;
var result = "result"
var UMENGREACT =React.createClass({

  render:function(){
    return (
      <View style={styles.container}>

      <TouchableOpacity onPress = {this._share}>
      <Text style={styles.welcome}>
        分享!
      </Text>
   </TouchableOpacity>
   <TouchableOpacity onPress={this._auth}>
   <Text style={styles.welcome}>
     授权!
   </Text>
</TouchableOpacity>
<TouchableOpacity onPress={this._getinfo}>
<Text style={styles.welcome}>
  用户资料!
</Text>

</TouchableOpacity>
<TouchableOpacity onPress={this._shareboard}>
<Text style={styles.welcome}>
  打开分享面板!
</Text>
</TouchableOpacity>

  </View>
);
},
_shareboard:function(){
  RNIOSAlert.shareboard('from react native ','http://dev.umeng.com/images/tab2_1.png','http://dev.umeng.com','fff',
  (message) =>{
    this.setState({result:message});
    alert(message);
  });

},
_share:function(){

      RNIOSAlert.show('from react native ','http://dev.umeng.com/images/tab2_1.png','http://dev.umeng.com','fff',
      (message) =>{
        this.setState({result:message});
        alert(message);
      });
},
    _auth:function(){
      RNIOSAlert.auth(
      (message) =>{
        this.setState({result:message});
        alert(message);
      });
    },
    _getinfo:function(){
      RNIOSAlert.getinfo(
      (message) =>{
        this.setState({result:message});
        alert(message);
      });
    }
});

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
});

AppRegistry.registerComponent('UMENGREACT', () => UMENGREACT);
