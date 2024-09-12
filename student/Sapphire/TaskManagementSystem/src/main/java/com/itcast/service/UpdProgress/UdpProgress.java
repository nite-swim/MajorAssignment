package com.itcast.service.UpdProgress;

import com.itcast.mapper.TaskMapper;
import com.itcast.mapper.UserMapper;
import com.itcast.mq.Producer;
import com.itcast.pojo.Task;
import com.itcast.pojo.TaskInstance;
import com.itcast.pojo.User;
import com.itcast.service.Impl.TaskServiceImpl;
import com.itcast.service.Impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Scanner;

@Service
public class UdpProgress {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    private Producer producer;
    @Autowired
    private Task task;
    @Autowired
    private User user;
    @Autowired
    private TaskServiceImpl taskServiceImpl;



    public void showAndChooseTask() throws InterruptedException {
        while (true) {
            //展示任务列表
            System.out.println("请输入用户名：");
            Scanner scanner = new Scanner(System.in);
            String username = scanner.nextLine();
            user = userMapper.getUserByName(username);
            Thread.sleep(1000);
            int level = user.getLevel();
            if (level > userServiceImpl.getMaxUserLevel()) {
                level = userServiceImpl.getMaxUserLevel();
            }
            List<String> taskList = taskMapper.getTaskNameByUserLevel(level);
            System.out.print("你可以完成的任务有：");
            for (String taskName : taskList) {
                System.out.print((taskList.indexOf(taskName) + 1) + "." + taskName + " ");
            }
            System.out.println();
            //选择任务
            chooseTask(user, taskList);
        }
    }

    private void chooseTask(User user, List<String> taskList) {
        //选择任务
        System.out.print("请输入序号选择任务：GO");
        Scanner scanner = new Scanner(System.in);
        int command = scanner.nextInt();
        if (command < 1 || command > taskList.size()) {
            System.out.println("指令有误，请重新输入");
            return;
        } else {
            String taskName = taskList.get(command - 1);
            task = taskMapper.getTaskByTaskName(taskName);
            int currentProgress = userServiceImpl.getCurrentProgress(user, task);
            System.out.print(task.getTaskGoal());
            System.out.println("(当前任务完成进度：" + currentProgress + "/" + task.getTime() + ")");
            if(currentProgress/task.getTime() == 1){
                System.out.println("当前任务已完成");
                return;
            }
            progressRecord(task, user);
        }
    }

    /**
     * 根据用户id和任务id判断task_instance表中是否有对应的记录
     * @param userId
     * @param taskId
     * @return
     */
    boolean hasRecord(Integer userId, Integer taskId) {
        if (userMapper.getTaskInstanceByUserIdAndTaskId(userId, taskId) != null){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 记录用户完成任务的进度，若表中原先没有该用户完成该任务的记录，就插入新纪录，否则就更新记录，并且更新用户等级和奖励
     * @param task
     * @param user
     */
    public void progressRecord(Task task, User user) {
        int userId = user.getId();
        System.out.println("请输入：");
        Scanner scanner = new Scanner(System.in);
        int taskId = task.getId();
        String taskName = task.getTaskName();
        if (scanner.nextLine().equalsIgnoreCase(task.getTaskType())) {
            if (hasRecord(userId, taskId)){
                if (userMapper.getTaskInstanceProgress(userId, taskId) == task.getTime()){
                    System.out.println("该任务已完成");
                }else {
                    //若表里有数据则更新progress
                    userMapper.updateTaskInstance(userId, taskId);
                    if (userMapper.getTaskInstanceProgress(userId, taskId) == task.getTime()){
                        System.out.println("该任务已完成");
                        //升级、奖励
                        producer.sendUserIdAndTaskId("task-management-system-msg", "L&R", user.getName(), taskName);
                        //升级之后如果做的还是相同的任务，需要把进度清零
                        cleanTaskProgress(userId, taskId);
                    }
                }
            }else {
                //若表里没有数据则插入数据
                TaskInstance taskInstance = new TaskInstance(1, userId, task.getId(), taskName, 1);
                producer.sendTaskInstance("task-management-system-msg", "tt", taskInstance);
            }
        }
    }
    void cleanTaskProgress(Integer userId, Integer taskId) {
        if(hasRecord(userId, taskId)){
            producer.sendCleanCommand("task-management-system-msg", "clean", userId, taskId);
        }
    }
}
