# zhouzh3-rocketmq4

发送消息
curl -X GET http://localhost:8080/send

返回结果
`{
    "sendStatus": "SEND_OK",
    "msgId": "7F000001721C63947C6B6C0E41A40000",
    "messageQueue":
    {
        "topic": "rocketmq4-topic",
        "brokerName": "DESKTOP-4JCOVQ1",
        "queueId": 0
    },
    "queueOffset": 0,
    "transactionId": "7F000001721C63947C6B6C0E41A40000",
    "offsetMsgId": "AC17200100002A9F000000000088651C",
    "regionId": "DefaultRegion",
    "traceOn": true
}`