package com.itcast.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Scanner;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    private int id;
    private String taskName;
    private int userLevel;
    private String taskType;
    private String taskReward;
    private String taskGoal;
    private int time;
}
