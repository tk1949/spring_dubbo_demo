package org.example.demo;

import org.apache.dubbo.config.annotation.Service;
import org.example.base.Snowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.Optional;

@Service
public class ExampleServer implements ExampleFace {

    @Autowired
    private ExampleDao exampleDao;

    @Override
    public String hello(String id) {
        Optional<Example> optional = exampleDao.findById(id);
        if (optional.isEmpty()) {
            return null;
        }
        return "Hello" + optional.get().getExampleID();
    }

    @Override
    public Example find(String exampleID) {
        Optional<Example> optional = exampleDao.findById(exampleID);
        return optional.orElse(null);
    }

    @Override
    public Example add(Example example) {
        example.setExampleID(Snowflake.instance().pkId());
        example.setCreateTime(System.currentTimeMillis());
        example.setStatus(1);
        return exampleDao.save(example);
    }

    @Override
    public boolean upload(Example example) {
        example.setUpdateTime(System.currentTimeMillis());
        exampleDao.save(example);
        return true;
    }

    @Override
    public boolean delete(String exampleID) {
        Optional<Example> optional = exampleDao.findById(exampleID);
        if (optional.isEmpty()) {
            return false;
        }
        Example example = optional.get();
        example.setUpdateTime(System.currentTimeMillis());
        example.setStatus(0);
        exampleDao.save(example);
        return true;
    }

    @Override
    public Page<Example> list(Example example, int size, int page) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createTime");
        Specification<Example> specification = (Specification<Example>) (root, cq, cb) -> {
            Predicate status = cb.equal(root.get("status").as(Integer.class), 1);
            return cb.and(status);
        };
        return exampleDao.findAll(specification, pageable);
    }
}