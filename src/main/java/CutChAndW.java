import java.util.ArrayList;

class СutChAndW {
    private int start;
    private int end;
    private ArrayList<String> result = new ArrayList<>();
    private StringBuilder cutter = new StringBuilder();

    СutChAndW(int start, int end) {
        this.start = start;
        this.end = end;
    }

    ArrayList<String> cutCh(ArrayList<String> lines) {
        for (String line: lines) {
            int variable = Math.min(line.length(), end);
            if (line.length() > start) result.add(cutter.append(line, start - 1, variable).toString());
            cutter.setLength(0);
        }
        return result;
    }

    ArrayList<String> cutW(ArrayList<String> lines) {
        int length = cutter.length();
        for (String line : lines) {
            String[] sizeLine = line.split(" ");
            int variable = Math.min(sizeLine.length, end);
            if (sizeLine.length >= start && sizeLine.length >= variable) {
                for (int i = start; i <= variable; i++)
                    cutter.append(sizeLine[i - 1]).append(" ");
                length = cutter.length();
            }
            if (length != 0) cutter.deleteCharAt(length - 1);
            result.add(cutter.toString());
            cutter.setLength(0);
        }
        return result;
    }
}
