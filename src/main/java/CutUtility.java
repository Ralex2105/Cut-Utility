import org.kohsuke.args4j.*;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.util.ArrayList;




public class CutUtility {
    @Option(name = "-o", metaVar = "OutputName", usage = "Output file name")
    private File output;

    @Option(name = "-c", metaVar = "CharsIndent", usage = "Indentation in chars")
    private boolean Char;

    @Option(name = "-w", metaVar = "IndentWords", usage = "indent in words", forbids = {"-c"})
    private boolean Word;

    @Argument(required = true, metaVar = "Range", usage = "Range of chars or words")
    private String range;

    @Argument(metaVar = "InputName", index = 1, usage = "Input file name")
    private File input;

    public static void main(String[] args) throws IOException {
        new CutUtility().cutting(args);
    }

    private void cutting(String[] args) throws IOException {
        CmdLineParser parser = new CmdLineParser(this);
        ArrayList<String> lines = new ArrayList<>();
        String line;

        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            System.err.println(e.getMessage());
            System.err.println("Command line: cut [-c|-w] [-o ofile] range [file]");
            parser.printUsage(System.err);
            return;
        }
        if (!(Char || Word)) {
            System.err.print("The option is not used");
            return;
        }
        if (!range.matches("^(\\d*-\\d*)|(\\d+-x)|(x-\\d+)$")) {
            System.err.print("Invalid range argument");
            return;
        }
        int start = 1;
        int end = Integer.MAX_VALUE;
        String[] variable = range.split("-");
        if (!variable[0].equals("x")) start = Integer.parseInt(variable[0]);
        if (!variable[1].equals("x")) end = Integer.parseInt(variable[1]);
        if (end < start) {
            System.err.print("Invalid range argument");
            return;
        }
        СutChAndW cutChAndW = new СutChAndW(start, end);


        if (input == null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            line = reader.readLine();
            while (!line.equals("-end")) {
                lines.add(line);
                line = reader.readLine();
            }
        } else {
            try (BufferedReader reader = new BufferedReader(new FileReader(input))) {
                line = reader.readLine();
                while (line != null) {
                    lines.add(line);
                    line = reader.readLine();
                }
            }
        }

        if (Char) lines = cutChAndW.cutCh(lines);
        if (Word) lines = cutChAndW.cutW(lines);

        if (output == null) {
            for (int i = 1; i < lines.size(); i++)
                System.out.println(lines.get(i - 1));
            System.out.print(lines.get(lines.size() - 1));
        } else {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(output))) {
                for (int i = 1; i < lines.size(); i++)
                    writer.write(lines.get(i - 1) + System.lineSeparator());
                writer.write(lines.get(lines.size() - 1));
            }
        }
    }
}