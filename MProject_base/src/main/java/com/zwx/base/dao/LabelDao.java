package com.zwx.base.dao;

import com.zwx.base.entity.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/*
* JpaSpecificationExecutor -> 复杂查询
* JpaRepository -> 基本增删改查
* */

public interface LabelDao extends JpaRepository<Label,String> , JpaSpecificationExecutor<Label> {

}
