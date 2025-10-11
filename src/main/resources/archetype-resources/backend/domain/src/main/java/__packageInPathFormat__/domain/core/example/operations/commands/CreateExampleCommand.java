package ${package}.domain.core.example.operations.commands;

public record CreateExampleCommand(String name) {

    public CreateExampleCommand {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Example name cannot be empty");
        }
    }

}
