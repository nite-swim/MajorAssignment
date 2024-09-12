package com.itcast.service.UpdProgress;

import com.google.gson.Gson;
import com.itcast.pojo.TaskInstance;
import com.itcast.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Slf4j
@Service
@RocketMQMessageListener(topic = "task-management-system-msg", selectorExpression = "tt", consumerGroup = "task-progress-update-consumer-group")
public class ProgressInsertListener implements RocketMQListener<MessageExt> {
    @Autowired
    private TaskService taskService;

    @Override
    public void onMessage(MessageExt messageExt) {
        System.out.println("监听器已启动");
        String s = new String(messageExt.getBody(), StandardCharsets.UTF_8);
        System.out.println(s);
        Gson gson = new Gson();
        TaskInstance taskInstance = gson.fromJson(s, TaskInstance.class);
        taskService.insertTaskInstance(taskInstance);
        System.out.println("添加成功！");
    }
}
