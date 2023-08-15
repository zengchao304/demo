package com.txxy.demo.domain;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.List;

/**
 * @Author zengch
 * @Date 2023-06-16
 **/
@Data
public class CheckInResponseDO implements Serializable {

    private static final long serialVersionUID = 3949381337505435105L;

    private Integer code;

    private String message;

    private List<CheckInDetailDO> list;

}
