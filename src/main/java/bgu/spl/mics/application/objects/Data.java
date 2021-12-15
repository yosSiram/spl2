package bgu.spl.mics.application.objects;

/**
 * Passive object representing a data used by a model.
 * Add fields and methods to this class as you see fit (including public methods and constructors).
 */
public class Data {
    /**
     * Enum representing the Data type.
     */
    public enum Type {
        Images, Text, Tabular
    }

    private Type type;
    private int processed;
    private int size;

    public Data(Type type,int size) {
        type=type;
        size=size;
        processed = 0;
    }

    public int getProcessed() {
        return processed;
    }

    public void incrementData() {
        processed+=1000;
    }

    public int getSize() {
        return size;
    }

    public Type getType() {
        return type;
    }
}
