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
public class UidAndTid implements Serializable {
    private Integer Uid;
    private Integer Tid;
}
