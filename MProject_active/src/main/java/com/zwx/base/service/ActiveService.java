package com.zwx.base.service;

import com.zwx.base.dao.ActiveDao;
import com.zwx.base.entity.Active;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ActiveService {
    @Autowired
    private ActiveDao activeDao;

    @Transactional
    @Cacheable(key="#id" ,cacheNames="active") //key:SpringEL
    //key:id值
    //value: 返回值active
    public Active findActiveById(String id){   //key
        return activeDao.findById(id).get();//value
    }

    @Transactional
    @CacheEvict(key="#active.id" ,cacheNames = "active")
    public void addOrUpdateActive(Active active){
        activeDao.save(active) ;
    }

    @Transactional
    @CacheEvict(key="#id" ,cacheNames = "active")
    public void deleteActiveById(String id){
        activeDao.deleteById(id);
    }

}