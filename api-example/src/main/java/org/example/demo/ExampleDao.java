package org.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ExampleDao extends JpaRepository<Example, String>, JpaSpecificationExecutor<Example> {

    Example findByExampleIDAndStatus(String exampleID, int status);
}