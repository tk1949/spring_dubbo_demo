package org.example.demo;

import org.example.base.CrudInterface;
import org.example.base.ListInterface;

public interface ExampleFace extends CrudInterface<Example, String>, ListInterface<Example> {

    String hello(String name);
}