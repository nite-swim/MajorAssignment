package com.itcast.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Scanner;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Repository
public class Task implements Serializable {
    private int id;
    private String taskName;
    private int userLevel;
    private String taskType;
    private String taskReward;
    private String taskGoal;
    private int time;
}
