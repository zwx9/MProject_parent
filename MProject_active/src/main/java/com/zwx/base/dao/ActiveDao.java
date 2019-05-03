package com.zwx.base.dao;

import com.zwx.base.entity.Active;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ActiveDao extends JpaRepository<Active,String>, JpaSpecificationExecutor<Active> {
}