package org.example.base;

public interface UniteFace<Model, ID> {

    Model find(ID id);

    Model add(Model model);

    void upload(Model model);

    void delete(ID id);
}