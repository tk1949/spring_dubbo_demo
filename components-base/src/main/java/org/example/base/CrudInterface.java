package org.example.base;

public interface CrudInterface<Model, ID> {

    Model find(ID id);

    Model add(Model model);

    boolean upload(Model model);

    boolean delete(ID id);
}