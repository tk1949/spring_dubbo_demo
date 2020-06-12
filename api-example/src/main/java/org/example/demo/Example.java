package org.example.demo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@ToString
@Entity
public class Example {

    @Id
    private String accountID;

    private String createID;
    private String updateID;
    private long createTime;
    private long updateTime;
}