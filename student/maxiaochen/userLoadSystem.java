package maxiaochen;

import java.util.HashMap; // 引入 HashMap 类
import java.util.Scanner;


public class userLoadSystem {
    public static int loginStatusData = 0;
    private static String userName = "";
    private static HashMap<String, String> password = new HashMap<String, String>();

    private static void setPassWd(String userName, String passWd){
        password.put(userName, passWd);
    }

    private static int startPassWd(String userName, String passwd){
        if(password.containsKey(userName))
        {
            if(password.get(userName).equals(passwd)){
                System.out.print("OK Please\n");
                return 1;
            }
            else {
                System.out.print("Wrong Passwd\n");
                return 0;
            }
        }
        else {
            System.out.print("Welcome, You are new User \n");
            setPassWd(userName, passwd);
            System.out.print("I have already registered! Please Login In \n");
            return 2;
        }
    }

    public static void setUserName(String user) {
        userName = user;
    }
    public static String getUserName(){
        return userName;
    }

    public static void authenticationPasswd(){
        System.out.print("Please Enter UserName And PassWd \n");
        Scanner scanner = new Scanner(System.in);

        System.out.println("Input Your UserName:");
        String userName = scanner.nextLine();
        System.out.println("UserName:" + userName);

        System.out.println("Input Your Passwd:");
        String passWd = scanner.nextLine();
        System.out.println("passWd:" + passWd);

        int statusNumber = startPassWd(userName, passWd);
        while(statusNumber != 1){
            System.out.println("Input Your UserName:");
            userName = scanner.nextLine();
            System.out.println("UserName:" + userName);

            System.out.println("Input Your Passwd:");
            passWd = scanner.nextLine();
            System.out.println("passWd:" + passWd);

            statusNumber = startPassWd(userName, passWd);


        }
        setUserName(userName);
        scanner.close();

        loginStatusData = 1;
    }
}
