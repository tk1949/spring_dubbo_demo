package org.example.base;

public interface ListInterface<Model> {

    org.springframework.data.domain.Page<Model> list(Model model, int size, int page);
}