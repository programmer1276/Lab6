package CommandBase;

public abstract class Command implements CommandInterface {
    String name;
    String description;

    public Command(String name, String description) {
        this.name = name;
        this.description = description;
    }
    @Override
    public String getDescr() {
        return description;
    }
    @Override
    public String getName() {
        return name;
    }
    @Override
    public abstract String execute(String[] args);
}
