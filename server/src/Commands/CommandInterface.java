package Commands;

public interface CommandInterface {
    String execute(String[] args);
    String getDescr();
    String getName();
}
