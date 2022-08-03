package test;

import java.util.ArrayList;
import test.Commands.Command;
import test.Commands.DefaultIO;

public class CLI {

    ArrayList<Command> commands;
    DefaultIO dio;
    Commands c;

    public CLI(DefaultIO dio) {
        this.dio = dio;
        c = new Commands(dio);
        commands = new ArrayList<>();
        commands.add(c.new UploadCommand());
        commands.add(c.new Settings());
        commands.add(c.new Detect());
        commands.add(c.new Results());
        commands.add(c.new UploadAnom());
        commands.add(c.new ExitCommand());
    }

    public void start() {
        int index = -1;
        while (index != 5) {
            dio.write("Welcome to the Anomaly Detection Server.\n");
            dio.write("Please choose an option:\n");
            for (int i = 0; i < commands.size(); i++) {
                dio.write((i + 1) + ". " + commands.get(i).description + "\n");
            }
            index = (int) dio.readVal() - 1;
            dio.readText(); // read the \n
            if (index >= 0 && index <= 6)
                commands.get(index).execute();
        }
    }

}
