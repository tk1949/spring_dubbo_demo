package org.example.demo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@ToString
public class Example {

    @Id
    private String accountID;

    private String createID;
    private String updateID;
    private long createTime;
    private long updateTime;
}