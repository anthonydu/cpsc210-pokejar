package persistence;

import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JsonFile {
    private Path filePath;

    public JsonFile(String filePath) throws IllegalArgumentException {
        this.filePath = Paths.get(filePath);
    }

    public void write(JSONObject jsonObject) throws IOException {
        Files.write(this.filePath, jsonObject.toString().getBytes());
    }

    public JSONObject read() throws IOException {
        return new JSONObject(new String(Files.readAllBytes(this.filePath)));
    }
}
