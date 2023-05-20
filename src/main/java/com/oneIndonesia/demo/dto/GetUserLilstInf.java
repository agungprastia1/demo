package com.oneIndonesia.demo.dto;

import java.util.Date;

public interface GetUserLilstInf {
    Long getId();
    String getUser();
    String getEmail();
    String getNoHp();
    String getRole();
    Date getCreateAt();
    Date getUpdateAt();
}
