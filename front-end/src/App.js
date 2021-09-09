import logo from './logo.svg';
import './App.css';
import * as dd from 'dingtalk-jsapi';
import axios from 'axios';
import React from 'react';
import './App.css';

class App extends React.Component{
  constructor(props) {
    super(props);
    this.state = {
      //内网穿透工具介绍:
      // https://developers.dingtalk.com/document/resourcedownload/http-intranet-penetration?pnamespace=app
      domain: "",
      corpId: '',
      authCode: '',
      userId: '',
      userName: '',
      groupId: '',
      title: '故障维修',
      topCardId: '',
      imCardId: '',
      msg: '一号机房发生故障，不能正常工作，请尽快维修处理!!!',
    }
  }
  render() {
    if(this.state.userId === ''){
      this.login();
    }
    return (
        <div className="App">
          <h2>故障处理</h2>
          <p><small>置顶功能pc端无法查看时，请在手机端查看</small></p>
          <p><button type="button" onClick={() => this.create()}>发生故障自动建群</button></p>
          <p><button type="button" onClick={() => this.sendIm()}>推送故障消息（IM卡片）</button></p>
          <p><button type="button" onClick={(e) => this.updateMsg(e,this.state.imCardId)}>更新消息</button></p>
          <p><button type="button" onClick={() => this.sendMsg()}>故障消息群置顶（置顶卡片）</button></p>
          <p><button type="button" onClick={(e) => this.updateMsg(e,this.state.topCardId)}>更新置顶消息（置顶卡片）</button></p>
          <p><button type="button" onClick={() => this.complete()}>故障修复取消置顶</button></p>
        </div>
    );
  }
  updateMsg(e, cardId){
    axios.post(this.state.domain + "/sceneGroup/updateMsg?msg=消息更新——故障修复&cardId=" + cardId
    ).then(res => {
      if (res && res.data.success) {
        if(res.data.data){
          alert("更新成功！")
        }else{
          alert("更新失败！")
        }
      } else {
        alert("updateMsg failed --->" + JSON.stringify(res));
      }
    }).catch(error => {
      alert("updateMsg err, ", JSON.stringify(error))
    })
  }
  sendIm(){
    axios.post(this.state.domain + "/sceneGroup/sendIm?groupId=" + this.state.groupId + "&msg=" + this.state.msg
    ).then(res => {
      if (res && res.data.success) {
        if(res.data.data){
          this.setState({
            imCardId:res.data.data
          })
          alert("发送成功！")
        }else{
          alert("发送失败！")
        }
      } else {
        alert("sendIm failed --->" + JSON.stringify(res));
      }
    }).catch(error => {
      alert("sendIm err, ", JSON.stringify(error))
    })
  }
  complete(){
    axios.post(this.state.domain + "/sceneGroup/complete?groupId=" + this.state.groupId + "&topCardId=" + this.state.topCardId
    ).then(res => {
      if (res && res.data.success) {
        alert("故障修复完成！");
      } else {
        alert("complete failed --->" + JSON.stringify(res));
      }
    }).catch(error => {
      alert("complete err, ", JSON.stringify(error))
    })
  }
  sendMsg(){
    axios.post(this.state.domain + "/sceneGroup/sendMsg?groupId=" + this.state.groupId + "&msg=" + this.state.msg
    ).then(res => {
      if (res && res.data.success) {
        let topCardId = res.data.data;
        alert('开启置顶消息成功！！！');
        this.setState({
          topCardId:topCardId
        })

      } else {
        alert("sendMsg failed --->" + JSON.stringify(res));
      }
    }).catch(error => {
      alert("sendMsg err, ", JSON.stringify(error))
    })
  }
  create(){
    axios.post(this.state.domain + "/sceneGroup/create?userId=" + this.state.userId + "&title=" + this.state.title
    ).then(res => {
      if (res && res.data.success) {
        let groupId = res.data.data;
        alert('建群成功！！！');
        this.setState({
          groupId:groupId
        })
      } else {
        alert("create failed --->" + JSON.stringify(res));
      }
    }).catch(error => {
      alert("create err, ", JSON.stringify(error))
    })
  }
  login(){
    axios.get(this.state.domain + "/getCorpId")
        .then(res => {
          if(res.data){
            this.loginAction(res.data);
          }
        }).catch(error => {
      alert("corpId err, ", JSON.stringify(error))
    })
  }
  loginAction(corpId) {
    alert("corpId: " +  corpId);
    let _this = this;
    dd.runtime.permission.requestAuthCode({
      corpId: corpId,//企业 corpId
      onSuccess : function(res) {
        // 调用成功时回调
        _this.state.authCode = res.code
        axios.get(_this.state.domain + "/login?authCode=" + _this.state.authCode
        ).then(res => {
          if (res && res.data.success) {
            let userId = res.data.data.userId;
            let userName = res.data.data.userName;
            alert('登录成功，你好' + userName);
            setTimeout(function () {
              _this.setState({
                userId:userId,
                userName:userName
              })
            }, 0)
          } else {
            alert("login failed --->" + JSON.stringify(res));
          }
        }).catch(error => {
          alert("httpRequest failed --->" + JSON.stringify(error))
        })
      },
      onFail : function(err) {
        // 调用失败时回调
        alert("requestAuthCode failed --->" + JSON.stringify(err))
      }
    });
  }

}

export default App;
