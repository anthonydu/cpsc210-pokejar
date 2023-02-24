package persistence;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JsonFile {
    /**
     * Path to a file
     */
    private Path filePath;

    /**
     * Constructs a JsonFile object with a path
     *
     * @param filePath a String that is a path to a file
     * @throws InvalidPathException if the path string cannot be converted to a Path
     */
    public JsonFile(String filePath) throws InvalidPathException {
        this.filePath = Paths.get(filePath);
    }

    /**
     * Overwrites a JSONObject to a file at this JsonFile's filePath.
     * Creates the file if a file doesn't exist at filePath
     *
     * @param jsonObject the JSONObject to write to this JsonFile
     * @throws IOException if an I/O error occurs reading from the stream
     */
    public void write(JSONObject jsonObject) throws IOException {
        Files.write(this.filePath, jsonObject.toString().getBytes());
    }

    /**
     * Reads and converts the content of a JsonFile into a JSONObject
     *
     * @return the JSONObject stored in this JsonFile
     * @throws IOException if an I/O error occurs writing to or creating the file
     * @throws JSONException if there is a syntax error in the source string or a duplicated key
     */
    public JSONObject read() throws IOException, JSONException {
        return new JSONObject(new String(Files.readAllBytes(this.filePath)));
    }
}
