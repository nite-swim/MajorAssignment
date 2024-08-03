package maxiaochen;

import maxiaochen.userLoadSystem.*;
import maxiaochen.constructStatus.*;
public class userRewardData {
    private int getLoginStatus(){
        return userLoadSystem.loginStatusData;
    }
    public void userAccessData(){
        int status = getLoginStatus();
        String userName = userLoadSystem.getUserName();
        String tips = " ";
        constructStatus st = new constructStatus();
        st.setTaskMap(userName, tips);
        if(status == 1)
        {
            System.out.println("Hi " + userName + " Welcome Task List");
            constructStatus.constructData.forEach((k,v) -> {
                System.out.println("任务序号1: " + k + "\t任务名称\t" + v.frontShowData + "\t奖励内容\t" + v.RewardData + "\n");
            });
        }
        else {
            System.out.println("未登录");
        }
    }
}
