package com.how2java.tmall.service.impl;

import com.how2java.tmall.mapper.PropertyMapper;
import com.how2java.tmall.pojo.Property;
import com.how2java.tmall.pojo.PropertyExample;
import com.how2java.tmall.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @ClassName PropertyServiceImpl
 * @Description TODO
 * @Author liqipiao
 * @Date 2019/12/29 0029 20:44
 **/
public class PropertyServiceImpl implements PropertyService {
    @Autowired
    PropertyMapper propertyMapper;
    @Override
    public void add(Property c) {
        propertyMapper.insert(c);
    }

    @Override
    public void delete(int id) {
        propertyMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Property c) {
        propertyMapper.updateByPrimaryKeySelective(c);
    }

    @Override
    public Property get(int id) {
        return propertyMapper.selectByPrimaryKey(id);
    }

    @Override
    public List list(int cid) {
        PropertyExample example =new PropertyExample();
        example.createCriteria().andCidEqualTo(cid);
        example.setOrderByClause("id desc");
        return propertyMapper.selectByExample(example);
    }
}
