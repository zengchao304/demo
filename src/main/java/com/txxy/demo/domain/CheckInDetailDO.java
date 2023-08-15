package com.txxy.demo.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author zengch
 * @Date 2023-06-16
 **/
@Data
public class CheckInDetailDO implements Serializable {

    private Integer id;

    private Integer user_id;

    private String time;

    private String asset;

    private String business;

    private String change;

    private String balance;

    private String detail;


}
