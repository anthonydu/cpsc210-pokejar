package persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JsonIOTest {
    JsonIO jsonIO;

    @BeforeEach
    public void setup() {
        jsonIO = new JsonIO("./data/example.json");
    }

    @Test
    public void testRead() {

    }

    @Test
    public void testWrite() {

    }
}
