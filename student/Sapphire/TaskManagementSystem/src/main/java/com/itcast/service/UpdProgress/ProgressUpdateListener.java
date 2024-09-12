package com.itcast.service.UpdProgress;

import com.google.gson.Gson;
import com.itcast.pojo.Task;
import com.itcast.pojo.UnameAndTname;
import com.itcast.pojo.User;
import com.itcast.service.Impl.TaskServiceImpl;
import com.itcast.service.Impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Slf4j
@Service
@RocketMQMessageListener(topic = "task-management-system-msg", selectorExpression = "L&R", consumerGroup = "task-progress-update-consumer-group")
public class ProgressUpdateListener implements RocketMQListener<MessageExt> {
    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    private TaskServiceImpl taskServiceImpl;
    @Override
    public void onMessage(MessageExt msg) {
        String s = new String(msg.getBody(), StandardCharsets.UTF_8);
        System.out.println(s);
        Gson gson = new Gson();
        UnameAndTname uidAndTid = gson.fromJson(s, UnameAndTname.class);
        String uName = uidAndTid.getUName();
        String tName = uidAndTid.getTName();
        User user = userServiceImpl.getUserByName(uName);
        Task task = taskServiceImpl.getTaskByName(tName);
        int userId = user.getId();
        userServiceImpl.updateLevel(userId);//数据库
        taskServiceImpl.updateUserLevelById(userId);//Redis
        userServiceImpl.userGainReward(user, task);
        System.out.println("更新成功！");
    }
}
