package prettite;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.nio.file.Paths;
import java.nio.file.Files;

public class Prettite {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter file path : ");
        String file = sc.nextLine();
        Prettite ob = new Prettite();
        ob.fixEndingBraces(file);
        
        try {
            // Read all lines from the file into a List<String>
            List<String> lines = Files.readAllLines(Paths.get(file));
            int br = 0;
            List<String> formattedLines = new ArrayList<>();
            // Print each line
            for (String line : lines) {
                if (line.equals("{")) {
                    br++;
                    for (int i = 1; i < br; i++) {
                        line = "\t" + line;
                    }
                    formattedLines.add(line);
                    continue;
                } else if (line.equals("}")) {
                    br--;
                }
                if (br != 0) {
                    for (int i = 1; i <= br; i++) {
                        line = "\t" + line;
                    }
                    formattedLines.add(line);
                } else if (br == 0)
                    formattedLines.add(line);
            }
            Files.write(Paths.get(file), formattedLines, StandardOpenOption.WRITE,
                    StandardOpenOption.TRUNCATE_EXISTING);
            ob.removeBlankLines(file);
            ob.sortImports(file);
            System.out.println("Successfully formatted the file.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fixEndingBraces(String file) {
        try {
            // Read all lines from the file into a List<String>
            List<String> lines = Files.readAllLines(Paths.get(file));
            List<String> fixedBraces = new ArrayList<>();
            for (String line : lines) {
                // Opening curly brace on the next line
                line = line.replaceAll("\\s*\\{\\s*$", "\n{");
                // Closing curly brace on a new line
                line = line.replaceAll("^\\s*\\}", "}\n");
                fixedBraces.add(line);
            }
            Files.write(Paths.get(file), fixedBraces, StandardOpenOption.WRITE,
                    StandardOpenOption.TRUNCATE_EXISTING);
            System.out.println("Successfully fixed braces.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void removeBlankLines(String file) {
        try {
            // Read all lines from the file into a List<String>
            List<String> lines = Files.readAllLines(Paths.get(file));
            List<String> nonBlankLines = new ArrayList<>();

            // Filter out blank lines
            for (String line : lines) {
                if (!line.trim().isEmpty()) {
                    nonBlankLines.add(line);
                }
            }

            // Write non-blank lines back to the file
            Files.write(Paths.get(file), nonBlankLines, StandardOpenOption.WRITE,
                    StandardOpenOption.TRUNCATE_EXISTING);

            System.out.println("Successfully removed blank lines.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void sortImports(String file) {
        try {
            // Read all lines from the file into a List<String>
            List<String> lines = Files.readAllLines(Paths.get(file));
            List<String> importLines = new ArrayList<>();

            // Extract import and #include statements
            for (String line : lines) {
                if (line.trim().startsWith("import ") || line.trim().startsWith("#include ")) {
                    importLines.add(line);
                }
            }

            // Sort import and #include statements
            Collections.sort(importLines);

            // Replace existing import and #include statements with sorted ones
            for (int i = 0, j = 0; i < lines.size(); i++) {
                if (lines.get(i).trim().startsWith("import ") || lines.get(i).trim().startsWith("#include ")) {
                    lines.set(i, importLines.get(j++));
                }
            }

            // Write back to the file
            Files.write(Paths.get(file), lines, StandardOpenOption.WRITE,
                    StandardOpenOption.TRUNCATE_EXISTING);

            System.out.println("Successfully sorted imports.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
