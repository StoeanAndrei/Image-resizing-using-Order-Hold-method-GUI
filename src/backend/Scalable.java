package backend;

public interface Scalable {
    public enum Preserve {
        WIDTH, HEIGHT
    }
    int start = 0;
    int firstQuarter = 1;
    int secondQuarter = 2;
    int thirdQuarter = 3;
    int fourthQuarter = 4;
    String fileExtension = "bmp";

    /**
     * Modifica rezolutia imaginii cu latimea si inaltimea specificata
     * @param newX latime
     * @param newY inaltime
     */
    default void invoke(int newX, int newY) {
        callThreads(newX, newY, 1);
    }

    /**
     * Modifica rezolutia si scaleaza cu factorul de zoom imaginea
     * @param newX latime
     * @param newY inaltime
     * @param scale factor de zoom
     */
    void invoke(int newX, int newY, double scale);

    /**
     * Modifica rezolutia imaginii cu pastrarea aspect-ratio original
     * @param size marimea proprietatii imaginii cu care se va modifica rezolutia
     * @param property proprietatea dupa care se va pastra aspect-ratio
     */
    void invoke(int size, Preserve property);

    /**
     * Modifica rezolutia imaginii cu pastrarea aspect-ratio original si scaleaza cu factorul de zoom
     * @param size marimea proprietatii imaginii cu care se va modifica rezolutia
     * @param property proprietatea dupa care se va pastra aspect-ratio
     * @param scale factor de zoom
     */
    void invoke(int size, Preserve property, double scale);
    void callThreads(int newX, int newY, double scale);
}
