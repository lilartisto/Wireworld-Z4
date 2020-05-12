package BoardFiles;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


public class FileManager {
    public static void writeMapToFile(Map map, String fileName)
    {
        try (
                var fileWriter = new FileWriter(fileName);
                var writer = new BufferedWriter(fileWriter);
        ) {
            writer.write(map.width + " " + map.height);
            writer.newLine();

            for (int i = 0; i < map.height; i++)
                for (int j = 0; j < map.width; j++)
                {
                    int state = map.getField(j, i);
                    String stateString = getStateAsString(state);

                    if (stateString == null)
                        continue;

                    writer.write(stateString + " " + j + " " + i);
                    writer.newLine();
                }
        } catch (IOException e)
        {
            System.err.println("Nie udało się zapisać do pliku");
        }
    }

    private static String getStateAsString(int state)
    {
        switch (state)
        {
            case Map.HEAD:
                return "HEAD";
            case Map.TAIL:
                return "TAIL";
            case Map.CONDUCTOR:
                return "CONDUCTOR";
            default:
                return null;
        }
    }
}
