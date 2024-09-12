package com.itcast.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Repository
public class User implements Serializable {
    private Integer id;
    private Integer level;
    private Integer gold;
    private Integer redPocket;
    private String name;
    private String password;
}
