package com.itcast.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskInstance implements Serializable {
    private int id;
    private int userId;
    private int taskId;
    private String taskName;
    private int progress;
}
