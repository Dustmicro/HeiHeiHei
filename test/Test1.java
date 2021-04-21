package com.kaikeba.test;

import com.kaikeba.util.DBUtil;

import java.sql.Connection;

public class Test1 {
    public static void main(String[] args) {
        Connection conn = DBUtil.getConn();

    }
}
