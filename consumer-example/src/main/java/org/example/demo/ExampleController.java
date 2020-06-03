package org.example.demo;

import org.apache.dubbo.config.annotation.Reference;
import org.example.monitor.Monitor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleController {

    @Reference
    private ExampleFace exampleFace;

    @GetMapping("/echo/{str}")
    @Monitor
    public String echo(@PathVariable String str) {
        return exampleFace.hello(str);
    }
}