package com.kaikeba.db;

import com.kaikeba.bean.User;

public interface BaseUserDao {

    /**
     * 用于新增用户人脸信息
     * @param user 用户对象
     * @return 新增的结果，大于0标识成功
     */
    int insert(User user);

    /**
     * 基于人脸标识码，进行用户次数的新增
     * @param face_id 人脸标识码
     * @return 新增后的用户全部信息
     */
    User count(String face_id);

    /**
     * 通过人脸标识码， 修改用户的姓名和备注信息
     * @param face_id 人脸标识码
     * @param user 用户的姓名和备注的信息对象
     * @return 修改的结果，大于0标识成功
     */
    int updateUserByFaceId(String face_id,User user);

    /**
     * 根据人脸标识码，增加人脸收录的次数
     * @param face_id 人脸标识码
     * @return 修改的结果，大于0标识成功
     */
    int updateCountByFaceId(String face_id,int count);

    /**
     * 根据人脸标识码，查询用户详细信息
     * @param face_id 人脸标识码
     * @return 查询的用户所有信息
     */
    User findUserByFaceId(String face_id);

}
