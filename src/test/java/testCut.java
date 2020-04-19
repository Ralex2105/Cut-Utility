import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class testCut {
    private Path outputFile = Paths.get("result.txt");
    private Path inputFile = Paths.get("src", "main", "resources", "testFile.txt");
    private String s = System.lineSeparator();
    private ByteArrayOutputStream output = new ByteArrayOutputStream();
    private PrintStream out = new PrintStream(output);
    private ArrayList<String> lines = new ArrayList<>();


    @Test
    void testConsoleAndTestCutCh1() throws IOException {
        System.setOut(out);
        ByteArrayInputStream input = new ByteArrayInputStream(("HelloQQQ" + s + "HelloQQQ" + s + "-end").getBytes());
        System.setIn(input);
        CutUtility.main(new String[]{"-c", "1-4"});
        assertEquals("Hell" + s + "Hell",
                output.toString());
        System.setOut(out);
    }

    @Test
    void testConsoleAndTestCutCh2() throws IOException {
        System.setOut(out);
        ByteArrayInputStream input = new ByteArrayInputStream(("MartinHell" + s + "MartinHell" + s + "-end").getBytes());
        System.setIn(input);
        CutUtility.main(new String[]{"-c", "5-x"});
        assertEquals("inHell" + s + "inHell",
                output.toString());
        System.setOut(out);
    }

    @Test
    void testConsoleAndTestCutCh3() throws IOException {
        System.setOut(out);
        ByteArrayInputStream input = new ByteArrayInputStream(("Hello" + s + "Hello" + s + "-end").getBytes());
        System.setIn(input);
        CutUtility.main(new String[]{"-c", "x-4"});
        assertEquals("Hell" + s + "Hell",
                output.toString());
        System.setOut(out);
    }
    @Test
    void testFileAndTestCutW1() throws IOException {
        CutUtility.main(new String[]{"-w", "-o", outputFile.toString(), "2-3", inputFile.toString()});
        try (BufferedReader reader = new BufferedReader(new FileReader(String.valueOf(outputFile)))) {
            for (int i = 0; i < 3; i++)
                lines.add(reader.readLine());
        }
        assertEquals("qwerty qwerty1", lines.get(0));
        assertEquals("qwerty qwerty2", lines.get(1));
        assertEquals("qwerty qwerty3", lines.get(2));
        File file = new File(outputFile.toString());
        file.delete();
    }


    @Test
    void testFileAndTestCutW2() throws IOException {
        CutUtility.main(new String[]{"-w", "-o", outputFile.toString(), "x-3", inputFile.toString()});
        try (BufferedReader reader = new BufferedReader(new FileReader(String.valueOf(outputFile)))) {
            for (int i = 0; i < 3; i++)
                lines.add(reader.readLine());
        }
        assertEquals("qwerty qwerty qwerty1", lines.get(0));
        assertEquals("qwerty qwerty qwerty2", lines.get(1));
        assertEquals("qwerty qwerty qwerty3", lines.get(2));
        File file = new File(outputFile.toString());
        file.delete();
    }

    @Test
    void errorsIncorrectRange1() throws IOException {
        System.setErr(out);
        CutUtility.main(new String[] {"-w", "4-1"});
        assertEquals("Invalid range argument", output.toString());
        System.setErr(System.err);
    }
    @Test
    void errorsIncorrectRAnge2() throws IOException {
        System.setErr(out);
        CutUtility.main(new String[] {"-w", "one-four"});
        assertEquals("Invalid range argument", output.toString());
        System.setErr(System.err);
    }
    @Test
    void errorsNotUsedFlag() throws IOException {
        System.setErr(out);
        CutUtility.main(new String[] {"w", "1-4"});
        assertEquals("The option is not used", output.toString());
        System.setErr(System.err);
    }
}