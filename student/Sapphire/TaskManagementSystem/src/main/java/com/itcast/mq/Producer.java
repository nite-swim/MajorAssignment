package com.itcast.mq;

import com.google.gson.Gson;
import com.itcast.pojo.*;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Producer {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    @Autowired
    private Gson gson;
    @Autowired
    private UnameAndTname uNameAndTname;
    @Autowired
    private UidAndTid uidAndTid;

    public void sendMessage(String topic, String msg) {
        rocketMQTemplate.convertAndSend(topic, msg);
    }

    public void sendMessage(String topic, Integer msg) {
        rocketMQTemplate.convertAndSend(topic, msg);
    }

    public void sendMessage(String topic, List<String> messages){
        rocketMQTemplate.convertAndSend(topic, messages);
    }

    public void sendMessageWithTag(String topic, String tag, String msg) {
        String destination = topic+":"+tag;
        rocketMQTemplate.syncSend(destination, MessageBuilder.withPayload(msg).build());
    }

    public void sendUserAndTask(String topic,String tag, Task task, User user) {
        String destination = topic+":"+tag;
        Map<String,String> message = new HashMap<>();
        String taskJson = gson.toJson(task);
        String userJson = gson.toJson(user);
        message.put("user", userJson);
        message.put("task", taskJson);
        rocketMQTemplate.syncSend(destination, message);
    }

    public void sendUserIdAndTaskId(String topic, String tag, String userName, String taskName) {
        String destination = topic+":"+tag;
        uNameAndTname = new UnameAndTname(userName, taskName);
        String json = gson.toJson(uNameAndTname);
        rocketMQTemplate.convertAndSend(destination, json);
    }

    public void sendTaskInstance(String topic, String tag, TaskInstance taskInstance) {
        String destination = topic+":"+tag;
        String json = gson.toJson(taskInstance);
        rocketMQTemplate.convertAndSend(destination, json);
    }

    public void sendCleanCommand(String topic, String tag, Integer userId, Integer taskId) {
        String destination = topic+":"+tag;
        uidAndTid =  new UidAndTid(userId,taskId);
        String json = gson.toJson(uidAndTid);
        rocketMQTemplate.convertAndSend(destination, json);
    }
}
