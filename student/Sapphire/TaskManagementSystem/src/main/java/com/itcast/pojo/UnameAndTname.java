package com.itcast.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnameAndTname implements Serializable {
    private String uName;
    private String tName;
}
