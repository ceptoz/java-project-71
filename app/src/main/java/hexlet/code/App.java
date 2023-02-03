package hexlet.code;

import picocli.CommandLine;

import java.io.IOException;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "gendiff", mixinStandardHelpOptions = true, version = "gendiff 1.0",
        description = "Compares two configuration files and shows a difference.")
public final class App implements Callable<Integer> {
    @CommandLine.Option(
            names = {"-f", "--format"},
            description = "output format",
            defaultValue = "stylish",
            showDefaultValue = CommandLine.Help.Visibility.ALWAYS,
            paramLabel = "format")
    private final String format = "stylish";
    @CommandLine.Parameters(index = "0", description = "path to first file", paramLabel = "filepath1")
    private String filePath1;
    @CommandLine.Parameters(index = "1", description = "path to second file", paramLabel = "filepath2")
    private String filePath2;

    @Override
    public Integer call() {
        try {
            Differ.generate(filePath1, filePath2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }
}
