package com.oneIndonesia.demo.dto;

import java.util.Date;

public interface GetApplicantListInf {
    Long getId();
    String getTitle();
    String getUser();
    String getCvName();
    String getCvPath();
    Date getCreateDate();
    String getStatus();
    String getRemark();
    String getCategory();
}
