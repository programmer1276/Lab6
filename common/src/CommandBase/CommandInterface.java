package CommandBase;

public interface CommandInterface {
    String execute(String[] args);
    String getDescr();
    String getName();
}
