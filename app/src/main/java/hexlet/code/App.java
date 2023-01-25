package hexlet.code;

import picocli.CommandLine;

import java.io.File;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "gendiff", mixinStandardHelpOptions = true, version = "gendiff 1.0",
        description = "Compares two configuration files and shows a difference.")
public class App implements Callable<Integer> {
    @CommandLine.Option(names = {"-f", "--format"}, description = "output format", defaultValue = "stylish", showDefaultValue = CommandLine.Help.Visibility.ALWAYS, paramLabel = "format")
    private String format = "stylish";
    @CommandLine.Parameters(index = "0", description = "path to first file", paramLabel = "filepath1")
    private File filepath1;
    @CommandLine.Parameters(index = "1", description = "path to second file", paramLabel = "filepath2")
    private File filepath2;

    @Override
    public Integer call() {
        System.out.println("Hello!");
        return 0;
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }
}