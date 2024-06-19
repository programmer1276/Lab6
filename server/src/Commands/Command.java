package Commands;

public abstract class Command implements CommandInterface {
    String name;
    String descr;
    String argument;  // заменено с Object на String

    public Command(String name, String descr) {
        this.name = name; this.descr = descr;
    }
    @Override
    public String getDescr() {
        return descr;
    }
    @Override
    public String getName() {
        return name;
    }
    @Override
    public abstract String execute(String[] args);
}
