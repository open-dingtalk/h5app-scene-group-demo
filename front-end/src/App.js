import logo from "./logo.svg"
import "./App.css"
import * as dd from "dingtalk-jsapi"
import axios from "axios"
import React from "react"
import "./App.css"
import { Button, message } from "antd"
import Form from "./components/Form"
import "antd/dist/antd.min.css"

class App extends React.Component {
  constructor(props) {
    super(props)
    this.state = {
      //内网穿透工具介绍:
      // https://developers.dingtalk.com/document/resourcedownload/http-intranet-penetration?pnamespace=app
      domain: "",
      corpId: "",
      authCode: "",
      userId: "",
      userName: "",
      groupId: "",
      topCardId: "",
      imCardId: "",
      showType: 0,
    }
  }
  render() {
    if (this.state.userId === "") {
      this.login()
    }
    return (
      <div className="App">
        <h2>场景群与消息卡片</h2>
        <p>
          <small>置顶功能pc端无法查看时，请在手机端查看</small>
        </p>
        {this.state.showType !== 0 && (
          <Form
            showType={this.state.showType}
            onSubmit={(data) => this.onSubmit(data)}
          />
        )}
        {!this.state.showType && (
          <div>
            <p>
              <Button
                type="primary"
                onClick={() => this.setState({ showType: 1 })}
              >
                创建场景建群
              </Button>
            </p>
            <p>
              <Button
                type="primary"
                onClick={() => this.setState({ showType: 2 })}
              >
                推送消息卡片到群
              </Button>
            </p>
            <p>
              <Button
                type="primary"
                onClick={() => this.setState({ showType: 3 })}
              >
                更新消息卡片到群
              </Button>
            </p>
            <p>
              <Button
                type="primary"
                onClick={() => this.setState({ showType: 4 })}
              >
                消息群置顶卡片
              </Button>
            </p>
            <p>
              <Button
                type="primary"
                onClick={(e) => this.setState({ showType: 5 })}
              >
                更新消息群置顶卡片
              </Button>
            </p>
            <p>
              <Button type="primary" onClick={() => this.complete()}>
                取消置顶卡片
              </Button>
            </p>
          </div>
        )}
      </div>
    )
  }
  onSubmit(data) {
    // 1 创建群 2 发送普通卡片 3 编辑普通卡片 4 发送置顶卡片 5 编辑置顶卡片
    if (this.state.showType === 1) {
      this.create(data)
    } else if (this.state.showType === 2) {
      this.sendIm(data)
    } else if (this.state.showType === 3) {
      this.updateMsg(data, this.state.imCardId)
    } else if (this.state.showType === 4) {
      this.sendMsg(data)
    } else {
      this.updateMsg(data, this.state.topCardId)
    }
  }
  updateMsg(e, cardId) {
    if (!cardId) {
      message.error("请先创建卡片消息")
      return
    }
    axios
      .post(
        this.state.domain +
          "/sceneGroup/updateMsg?msg=" +
          e.msg +
          "&cardId=" +
          cardId
      )
      .then((res) => {
        if (res && res.data.success) {
          message.success("更新成功！")
          this.setState({ showType: 0 })
        } else {
          message.error("更新失败！")
          alert("updateMsg failed --->" + JSON.stringify(res))
        }
      })
      .catch((error) => {
        alert("updateMsg err, ", JSON.stringify(error))
      })
  }
  sendIm(data) {
    axios
      .post(
        this.state.domain +
          "/sceneGroup/sendIm?groupId=" +
          this.state.groupId +
          "&msg=" +
          data.msg
      )
      .then((res) => {
        if (res && res.data.success) {
          if (res.data.data) {
            this.setState({
              imCardId: res.data.data,
              showType: 0,
            })
            message.success("发送成功！")
          } else {
            message.error("发送失败！")
          }
        } else {
          alert("sendIm failed --->" + JSON.stringify(res))
        }
      })
      .catch((error) => {
        alert("sendIm err, ", JSON.stringify(error))
      })
  }
  complete() {
    axios
      .post(
        this.state.domain +
          "/sceneGroup/complete?groupId=" +
          this.state.groupId +
          "&topCardId=" +
          this.state.topCardId
      )
      .then((res) => {
        if (res && res.data.success) {
          this.setState({ showType: 0 })
          message.success("修复完成！")
        } else {
          alert("complete failed --->" + JSON.stringify(res))
        }
      })
      .catch((error) => {
        alert("complete err, ", JSON.stringify(error))
      })
  }
  sendMsg(data) {
    axios
      .post(
        this.state.domain +
          "/sceneGroup/sendMsg?groupId=" +
          this.state.groupId +
          "&msg=" +
          data.msg
      )
      .then((res) => {
        if (res && res.data.success) {
          let topCardId = res.data.data
          message.success("开启置顶消息成功！！！")
          this.setState({
            topCardId: topCardId,
            showType: 0,
          })
        } else {
          alert("sendMsg failed --->" + JSON.stringify(res))
        }
      })
      .catch((error) => {
        alert("sendMsg err, ", JSON.stringify(error))
      })
  }
  create(data) {
    axios
      .post(
        this.state.domain +
          "/sceneGroup/create?userId=" +
          this.state.userId +
          "&title=" +
          data.title
      )
      .then((res) => {
        if (res && res.data.success) {
          let groupId = res.data.data
          message.success("建群成功！！！")
          this.setState({
            groupId: groupId,
            showType: 0,
          })
        } else {
          alert("create failed --->" + JSON.stringify(res))
        }
      })
      .catch((error) => {
        alert("create err, ", JSON.stringify(error))
      })
  }
  login() {
    axios
      .get(this.state.domain + "/getCorpId")
      .then((res) => {
        if (res.data) {
          this.loginAction(res.data)
        }
      })
      .catch((error) => {
        alert("corpId err, ", JSON.stringify(error))
      })
  }
  loginAction(corpId) {
    // alert("corpId: " +  corpId);
    let _this = this
    dd.runtime.permission.requestAuthCode({
      corpId: corpId, //企业 corpId
      onSuccess: function (res) {
        // 调用成功时回调
        _this.state.authCode = res.code
        axios
          .get(_this.state.domain + "/login?authCode=" + _this.state.authCode)
          .then((res) => {
            if (res && res.data.success) {
              let userId = res.data.data.userId
              let userName = res.data.data.userName
              message.success("登录成功，你好" + userName)
              setTimeout(function () {
                _this.setState({
                  userId: userId,
                  userName: userName,
                })
              }, 0)
            } else {
              alert("login failed --->" + JSON.stringify(res))
            }
          })
          .catch((error) => {
            alert("httpRequest failed --->" + JSON.stringify(error))
          })
      },
      onFail: function (err) {
        // 调用失败时回调
        alert("requestAuthCode failed --->" + JSON.stringify(err))
      },
    })
  }
}

export default App
