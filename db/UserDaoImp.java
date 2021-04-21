package com.kaikeba.db;

import com.kaikeba.bean.User;
import com.kaikeba.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//尽可能晚的链接数据库，尽可能早的释放数据库
public class UserDaoImp implements BaseUserDao{
    private static final String SQL_INSERT = "insert into user(face_id,city,logintime) values(?,?,?)";
    private static final String SQL_UPDATE_USER_BY_FACEID = "update user set username=?,description=? where face_id=?";
    private static final String SQL_UPDATE_COUNT_BY_FACEID = "update user set count=?,logintime=? where face_id=?";
    private static final String SQL_FIND_USER_BY_FACEID = "select * from user where face_id=?";

    //时间戳：从格林威治历开始（1970-1-1）到现在的13位毫秒数
    @Override
    public int insert(User user) {
        //1. 获取数据库链接
        Connection conn = DBUtil.getConn();
        //2. 预编译执行SQL的环境
        PreparedStatement state = null;
        try {
            state = conn.prepareStatement(SQL_INSERT);
            //3. 填充预编译的参数
            state.setString(1,user.getFace_id());
            state.setString(2,user.getCity());
            state.setLong(3,System.currentTimeMillis());
            //4. 执行存储
            int rowCount = state.executeUpdate();
            return rowCount;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            //5. 释放链接
            DBUtil.close(conn,state,null);
        }
        return -1;
    }

    @Override
    public User count(String face_id) {
        //1. 查询信息
        User user = findUserByFaceId(face_id);
        //判断时间间隔，是否符合要求，符合则收录
        //TODO 60000:表示间隔60秒收录一次，如需修改间隔时间，更改数字即可
        if(System.currentTimeMillis()-user.getLoginTime()>60000){
            user.setCount(user.getCount()+1);
            //2. 修改次数
            updateCountByFaceId(face_id,user.getCount());
        }
        return user;
    }

    @Override
    public int updateUserByFaceId(String face_id, User user) {
        //1. 获取数据库链接
        Connection conn = DBUtil.getConn();
        //2. 预编译执行SQL的环境
        PreparedStatement state = null;
        try {
            state = conn.prepareStatement(SQL_UPDATE_USER_BY_FACEID);
            //3. 填充预编译的参数
            state.setString(1,user.getUserName());
            state.setString(2,user.getDescription());
            state.setString(3,face_id);
            //4. 执行修改
            int rowCount = state.executeUpdate();
            return rowCount;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            //5. 释放链接
            DBUtil.close(conn,state,null);
        }
        return -1;
    }

    @Override
    public int updateCountByFaceId(String face_id,int count) {
        //1. 获取数据库链接
        Connection conn = DBUtil.getConn();
        //2. 预编译执行SQL的环境
        PreparedStatement state = null;
        try {
            state = conn.prepareStatement(SQL_UPDATE_COUNT_BY_FACEID);
            //3. 填充预编译的参数
            state.setInt(1,count);
            state.setLong(2,System.currentTimeMillis());
            state.setString(3,face_id);
            //4. 执行修改
            int rowCount = state.executeUpdate();
            return rowCount;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            //5. 释放链接
            DBUtil.close(conn,state,null);
        }
        return -1;
    }

    @Override
    public User findUserByFaceId(String face_id) {
        //1. 获取数据库链接
        Connection conn = DBUtil.getConn();
        //2. 预编译执行SQL的环境
        PreparedStatement state = null;
        ResultSet rs = null;
        try {
            state = conn.prepareStatement(SQL_FIND_USER_BY_FACEID);
            //3. 填充预编译的参数
            state.setString(1,face_id);
            //4. 执行查询
            rs = state.executeQuery();
            //5. 判断结果是否存在，存在则取出信息
            if(rs.next()){
                int id = rs.getInt("id");
                int count = rs.getInt("count");
                long loginTime = rs.getLong("loginTime");
                String username = rs.getString("username");
                String city = rs.getString("city");
                String description = rs.getString("description");
                return new User(id,face_id,username,description,city,count,loginTime);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            //*. 释放链接
            DBUtil.close(conn,state,rs);
        }
        return null;
    }
}
