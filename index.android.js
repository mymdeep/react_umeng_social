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
  Text,
  View
} from 'react-native';

var ShareView = require('./share.js');
var result = "result"
var UMENGREACT =React.createClass({

  render:function(){
    return (
      <View style={styles.container}>

      <TouchableOpacity onPress = {this._share}>
   </TouchableOpacity>
   <TouchableOpacity onPress={this._auth}>
   <Text style={styles.welcome}>
     auth!
   </Text>
</TouchableOpacity>
<TouchableOpacity onPress={this._getinfo}>
<Text style={styles.welcome}>
  get user info!
</Text>
</TouchableOpacity>
  </View>
);
},
_share:function(){
    ShareView.share('sssss','http://dev.umeng.com/images/tab2_1.png',0,(code,message) =>{
      this.setState({result:message});
      alert(message);
    });
},
    _auth:function(){
      ShareView.auth((message) =>{
        this.setState({result:message});
        alert(message);
      });
    },
    _getinfo:function(){
      ShareView.get((message) =>{
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
