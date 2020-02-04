package com.how2java.tmall.service.impl;

import com.how2java.tmall.mapper.UserMapper;
import com.how2java.tmall.pojo.User;
import com.how2java.tmall.pojo.UserExample;
import com.how2java.tmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName UserServiceImpl
 * @Description TODO
 * @Author liqipiao
 * @Date 2020/1/9 0009 16:21
 **/
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserMapper userMapper;
    @Override
    public void add(User user) {
        userMapper.insert(user);
    }

    @Override
    public void update(User user) {
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public void delete(int id) {
        userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public User get(int id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public List list() {
        UserExample example=new UserExample();
        example.setOrderByClause("id desc");
        return userMapper.selectByExample(example);
    }

    /**
     * 判断某个名称是否已经被使用过了。
     * @param name 用户名
     * @return 如果没有使用过返回false
     */
    @Override
    public boolean isExist(String name) {
        UserExample example=new UserExample();
        example.createCriteria().andNameEqualTo(name);
        List<User> result=userMapper.selectByExample(example);
        if (!result.isEmpty()){
            return true;
        }
        return false;
    }

    /**
     * 用于全部太登录
     * @param name 用户名
     * @param password 密码
     * @return
     */
    @Override
    public User get(String name, String password) {
        UserExample example=new UserExample();
        example.createCriteria().andNameEqualTo(name).andPasswordEqualTo(password);
        List<User> result=userMapper.selectByExample(example);
        if (result.isEmpty()){
            return null;
        }
        return result.get(0);
    }
}
