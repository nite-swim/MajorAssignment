package maxiaochen;

public class taskStatus {
    /*前端展示任务名称*/
    public String frontShowData = "";
    /*前端展示奖励名称*/
    public String RewardData = "";
    /*用户名称*/
    public String UserNama = "";
    /*用户名称*/
    public int RewardStatus = 0;

    public void setFrontShowData(String frontShowData) {
        this.frontShowData = frontShowData;
    }
    public void setRewardData(String RewardData) {
        this.RewardData = RewardData;
    }
    public void setUserNama(String UserNama) {
        this.UserNama = UserNama;
    }

    public void setRewardStatus(String RewardStatusName){
        if(RewardStatusName.equals("Read Action"))
        {
            this.RewardStatus = 1;
        }
    }

}
