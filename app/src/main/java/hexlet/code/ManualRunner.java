package hexlet.code;

import java.io.IOException;

public class ManualRunner {
    public static void main(String[] args) throws IOException {
        System.out.println("Hello, world!");

        String result = Differ
                .generate("src/test/resources/testdata1.json",
                        "src/test/resources/testdata2.json");

        System.out.println("Goodbye, user!");
    }
}
