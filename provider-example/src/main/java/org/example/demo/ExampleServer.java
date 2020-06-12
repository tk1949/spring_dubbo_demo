package org.example.demo;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

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
        return "Hello" + optional.get().getAccountID();
    }

    @Override
    public Example find(String s) {
        return null;
    }

    @Override
    public Example add(Example example) {
        return null;
    }

    @Override
    public void upload(Example example) {

    }

    @Override
    public void delete(String s) {

    }
}