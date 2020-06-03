package org.example.demo;

import org.apache.dubbo.config.annotation.Service;
import org.example.base.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

@Service
public class ExampleServer implements ExampleFace {

    @Autowired
    private ExampleDao exampleDao;

    @Override
    public String hello(String id) {
        Optional<Example> optional = exampleDao.findById(id);
        if (optional.isEmpty()) {
            throw new BusinessException("Data does not exist");
        }
        return "Hello" + optional.get().getAccountID();
    }
}