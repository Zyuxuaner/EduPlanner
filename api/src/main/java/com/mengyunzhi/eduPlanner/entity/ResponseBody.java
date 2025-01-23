package com.mengyunzhi.eduPlanner.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class ResponseBody {
    @Id
    private boolean status;
    private String message;
    private String data;
}
