import react, { useEffect } from "react"
import { Form, Input, Button } from "antd"

// showType 1 创建群 2 发送普通卡片 3 编辑普通卡片 4 发送置顶卡片 5 编辑置顶卡片
const Work = (props) => {
  const [form] = Form.useForm()

  const onSubmit = (data) => {
    props.onSubmit(data)
  }
  return (
    <div>
      <Form form={form} onFinish={onSubmit}>
        {props.showType === 1 && (
          <Form.Item
            label="群名称"
            name="title"
            rules={[{ required: true, message: "请输入群名称" }]}
          >
            <Input placeholder="请输入群名称" />
          </Form.Item>
        )}
        {(props.showType === 2 || props.showType === 3) && (
          <Form.Item
            label={props.showType === 2 ? "卡片消息" : "更新卡片消息"}
            name="msg"
            rules={[{ required: true, message: "请输入卡片消息内容" }]}
          >
            <Input placeholder="请输入卡片消息" />
          </Form.Item>
        )}
        {(props.showType === 4 || props.showType === 5) && (
          <Form.Item
            label={props.showType === 4 ? "置顶卡片消息" : "更新置顶卡片消息"}
            name="msg"
            rules={[{ required: true, message: "请输入卡片消息内容" }]}
          >
            <Input placeholder="请输入置顶卡片消息" />
          </Form.Item>
        )}
        <Button htmlType="submit" type="primary">
          提交
        </Button>
      </Form>
    </div>
  )
}

export default Work
