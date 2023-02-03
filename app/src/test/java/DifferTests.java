import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import hexlet.code.Differ;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DifferTests {
    @Test
    public void checkPositive() throws IOException {
        String expected = "{\n"
                + "    host: hexlet.io\n"
                + "  - method: GET\n"
                + "  - port: 8888\n"
                + "  + port: 443\n"
                + "  + protocol: http\n"
                + "    resources: available\n"
                + "}";

        String actual = Differ.generate(
                "src/test/resources/testdata1.json",
                "src/test/resources/testdata2.json"
        );
        assertEquals(actual, expected);
    }

    @Test()
    public void checkNonexistentFiles() {
        try {
            Differ.generate("griffindor", "ravenclaw");
        } catch (IOException e) {
            assertNotEquals("", e.getMessage(), "Files not found by specified path");
        }
    }

    @Test
    public void checkNonDeserializable() {
        assertThrows(MismatchedInputException.class, () -> Differ.generate(
                "src/test/resources/testdata3.json",
                "src/test/resources/testdata4.json"));
    }
}
