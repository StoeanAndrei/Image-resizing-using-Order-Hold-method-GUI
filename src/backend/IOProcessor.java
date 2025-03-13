package backend;

public abstract class IOProcessor {
    protected String inputFileName;
    protected String outputFileName;

    public String getInputFileName() {
        return inputFileName;
    }

    public String getOutputFileName() {
        return outputFileName;
    }
}
