package org.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExampleDao extends JpaRepository<Example, String> {
}