package com.zwx.base.dao;

import com.zwx.base.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface CompanyDao extends JpaRepository<Company,String> , JpaSpecificationExecutor<Company> {
    public List<Company> findByIsPopular(String isHot);
}
