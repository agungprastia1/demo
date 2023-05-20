package com.oneIndonesia.demo.dto;

import java.util.Date;

public interface GetDetailPostinganInf {
    Long getId();
    String getTitle();
    String getCategory();
    String getRecruiter();
    String getDescription();
    Date getDueDate();
    Long getSalary();
    Date getCreateAt();
    Date getUpdate();
}
