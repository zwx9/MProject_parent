package com.zwx.base.service;

import com.zwx.base.dao.LabelDao;
import com.zwx.base.entity.Label;
import com.zwx.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class LabelService {
    @Autowired
    private LabelDao labelDao;
    @Autowired
    private IdWorker idWorker;

    //查询全部
    public List<Label> findAll(){
        return labelDao.findAll();
    }

    //根据id查询
    public Label findLabelById(String id){
        return labelDao.findById(id).get();
    }

    //增加（自动生成id）
    public void saveLabel(Label label){
        label.setId(idWorker.nextId()+"");  //  -> 雪花算法生成id
        labelDao.save(label);
    }

    //更新（根据id修改）
    public void updateLabel(Label label){
        labelDao.save(label);
    }

    //删除
    public void deleteLabelById(String id){
        labelDao.deleteById(id);
    }

    //构建查询条件
    public Specification<Label> createSpecification(Map queryMap){
        return new Specification<Label>() {
            @Override
            public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if(queryMap.get("fans") != null && !queryMap.get("fans").equals("")) {
                    Predicate pl = criteriaBuilder.like(root.get("fans").as(String.class), "%" + queryMap.get("fans") + "%");
                    predicates.add(pl);
                }

                if(queryMap.get("state") != null && !queryMap.get("state").equals("")) {
                    Predicate pl = criteriaBuilder.like(root.get("state").as(String.class), "%" + queryMap.get("state") + "%");
                    predicates.add(pl);
                }

                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };

    }

    //根据条件查询
    public List<Label> findLabels(Map queryMap){
        Specification<Label> specification = createSpecification(queryMap);
        return labelDao.findAll(specification);
    }

    //根据条件查询分页显示
    public Page findLabels(Map queryMap, int start, int pageSize){
        Specification<Label> specification = createSpecification(queryMap);
        PageRequest pageRequest = PageRequest.of(start - 1, pageSize);
        return labelDao.findAll(specification,pageRequest);
    }

}
