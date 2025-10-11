package ${package}.domain.core.example.operations.results;

import ${package}.domain.core.example.messages.ExampleMessageKey;

public record CreateExampleResult(Long id, ExampleMessageKey messageKey, String name) { }
