package mancala;

import java.io.Serializable;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.FileInputStream;

public class Saver {
    public static void saveObject(Serializable toSave, String filename) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("assets/" + filename))) {
            out.writeObject(toSave);
        }
    }

    public static Serializable loadObject(final String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream into = new ObjectInputStream(new FileInputStream("assets/" + filename))) {
            return (Serializable) into.readObject();
        }
    }
}