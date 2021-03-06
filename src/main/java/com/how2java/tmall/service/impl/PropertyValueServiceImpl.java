package com.how2java.tmall.service.impl;

import com.how2java.tmall.mapper.PropertyValueMapper;
import com.how2java.tmall.pojo.Product;
import com.how2java.tmall.pojo.Property;
import com.how2java.tmall.pojo.PropertyValue;
import com.how2java.tmall.pojo.PropertyValueExample;
import com.how2java.tmall.service.PropertyService;
import com.how2java.tmall.service.PropertyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName PropertyValueServiceImpl
 * @Description TODO
 * @Author liqipiao
 * @Date 2020/1/9 0009 15:50
 **/
@Service
public class PropertyValueServiceImpl implements PropertyValueService {
    @Autowired
    PropertyValueMapper propertyValueMapper;
    @Autowired
    PropertyService propertyService;
    @Override
    public void init(Product product) {
        List<Property> properties=propertyService.list(product.getCid());
        for (Property property : properties){
            PropertyValue propertyValue=get(property.getId(),product.getId());
            if (null==propertyValue){
                propertyValue=new PropertyValue();
                propertyValue.setPid(product.getId());
                propertyValue.setPtid(property.getId());
                propertyValueMapper.insert(propertyValue);
            }
        }
    }

    @Override
    public void update(PropertyValue propertyValue) {
        propertyValueMapper.updateByPrimaryKeySelective(propertyValue);
    }

    @Override
    public PropertyValue get(int ptid, int pid) {
        PropertyValueExample example=new PropertyValueExample();
        example.createCriteria().andPtidEqualTo(ptid).andPidEqualTo(pid);
        List<PropertyValue> propertyValues=propertyValueMapper.selectByExample(example);
        if (propertyValues.isEmpty()){
            return null;
        }
        return propertyValues.get(0);
    }

    @Override
    public List<PropertyValue> list(int pid) {
        PropertyValueExample example=new PropertyValueExample();
        example.createCriteria().andPidEqualTo(pid);
        List<PropertyValue> result=propertyValueMapper.selectByExample(example);
        for (PropertyValue propertyValue : result){
            Property property=propertyService.get(propertyValue.getPtid());
            propertyValue.setProperty(property);
        }
        return result;
    }
}
