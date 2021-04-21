package com.kaikeba.controller;

import com.kaikeba.bean.Result;
import com.kaikeba.bean.User;
import com.kaikeba.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/v1/user/count")
public class CountServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //在这里处理请求
        //1.    接收传来的参数
        String face_id = request.getParameter("face_id");
        //2.    将参数，传递Service，进行数据库的存储
        User user = UserService.count(face_id);
        //在这里进行响应
        //3.    根据存储的结果准备不同的响应内容
        Result result = null;
        if(user!=null){
            result = new Result(user,0,"更新成功");
        }else{
            result = new Result(-1,"更新失败");
        }
        //4.    进行内容的JSON格式转换
        String json = result.toString();
        //5.    将内容响应给小程序
        response.getWriter().append(json);
    }
}
