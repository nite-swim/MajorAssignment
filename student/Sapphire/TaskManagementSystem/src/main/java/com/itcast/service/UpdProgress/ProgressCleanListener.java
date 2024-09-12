package com.itcast.service.UpdProgress;

import com.google.gson.Gson;
import com.itcast.pojo.UidAndTid;
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
@RocketMQMessageListener(topic = "task-management-system-msg", selectorExpression = "clean", consumerGroup = "task-progress-update-consumer-group")
public class ProgressCleanListener implements RocketMQListener<MessageExt> {
    @Autowired
    private TaskService taskService;
    @Override
    public void onMessage(MessageExt messageExt) {
        String s = new String(messageExt.getBody(), StandardCharsets.UTF_8);
        Gson gson = new Gson();
        UidAndTid uidAndTid = gson.fromJson(s, UidAndTid.class);
        taskService.cleanProgress(uidAndTid.getUid(), uidAndTid.getTid());
    }
}
