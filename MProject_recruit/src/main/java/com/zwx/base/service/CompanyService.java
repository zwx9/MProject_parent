package com.zwx.base.service;

import com.zwx.base.dao.CompanyDao;
import com.zwx.base.entity.Company;
import com.zwx.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CompanyService {
    @Autowired
    private CompanyDao companyDao;


    public List<Company> popularList(String isPopular){

        return companyDao.findByIsPopular(isPopular) ;
    }
}
