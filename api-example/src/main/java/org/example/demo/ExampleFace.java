package org.example.demo;

import org.example.base.UniteFace;

public interface ExampleFace extends UniteFace<Example, String> {

    String hello(String name);
}