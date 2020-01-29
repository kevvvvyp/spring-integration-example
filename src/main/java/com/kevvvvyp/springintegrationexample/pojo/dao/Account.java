package com.kevvvvyp.springintegrationexample.pojo.dao;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Account {
    private String name;
    private long number;
    private double balance;
}
