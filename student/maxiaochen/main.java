package maxiaochen;
import static maxiaochen.userLoadSystem.*;
public class main {
    public static void main(String[] args) {
        userLoadSystem sysData = new userLoadSystem();
        userRewardData sysUser = new userRewardData();
        sysData.authenticationPasswd();
        sysUser.userAccessData();
    }
}
