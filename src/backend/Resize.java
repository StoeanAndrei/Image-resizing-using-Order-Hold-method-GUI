package backend;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.imageio.ImageIO;

public class Resize extends ImageProcessor implements Scalable {
    //private final Scanner scanner = new Scanner(System.in);

	public Resize(String pathImage) {
    	super();
        readInputFileName(pathImage);
        readOutputFileName(pathImage);
        try {
            inputImage = ImageIO.read(new File(inputFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void callThreads(int newX, int newY, double scale) {
        // Se creeaza bufferul de scriere pentru noua imagine
        outputImage = new BufferedImage(newX, newY, BufferedImage.TYPE_INT_RGB);

        // Folosim executor service cu un thread alocat pentru o implementare de pipeline
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        // Element de sincronizare care ne va ajuta sa asteptam rezultatul final al prelucrarii
        CountDownLatch countDownLatch = new CountDownLatch(4);

        // Se trimite primul task de prelucrare cu primul sfert de imagine
        executorService.submit(
                new ResizeTask(
                        newX,
                        newY,
                        Scalable.firstQuarter,
                        executorService,
                        countDownLatch,
                        this,
                        scale
                )
        );

        // Se asteapta terminarea celor 4 etape din pipeline
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Odata ce toate cele 4 etape s-au incheiat, putem inchide executor service pentru ca programul sa se poata termina
        executorService.shutdownNow();
    }

    @Override
    public void invoke(int size, Preserve property) {
        if (property == Preserve.WIDTH) {
            double aspectRatio = (double)inputImage.getWidth() / inputImage.getHeight();
            int newY = (int) (size / aspectRatio);
            callThreads(size, newY, 1);
        }
        else if (property == Preserve.HEIGHT) {
            double aspectRatio = (double)inputImage.getWidth() / inputImage.getHeight();
            int newX = (int) (size / aspectRatio);
            callThreads(newX, size, 1);
        }
    }

    @Override
    public void invoke(int newX, int newY, double scale) {
        newX = (int)(newX * scale);
        newY = (int)(newY * scale);
        callThreads(newX, newY, scale);
    }

    @Override
    public void invoke(int size, Preserve property, double scale) {
        if (property == Preserve.WIDTH) {
            double aspectRatio = (double)inputImage.getWidth() / inputImage.getHeight();
            int newY = (int) (size * scale / aspectRatio);
            callThreads(size, newY, scale);
        }
        else if (property == Preserve.HEIGHT) {
            double aspectRatio = (double)inputImage.getWidth() / inputImage.getHeight();
            int newX = (int) (size * scale / aspectRatio);
            callThreads(newX, size, scale);
        }
    }

    /**
     * Metoda ce va citi numele fisierului de intrare de la stdin
     */
    @Override
    protected void readInputFileName(String pathImage) {
        //System.out.println("Introduce numele fisierului de intrare");
        //inputFileName = scanner.nextLine();
        //System.out.println("\n");
    	String directory = new String();
        directory = pathImage.replace("/", "\\");

        //System.out.println(directory);
        
    	inputFileName = directory;
    }

    /**
     * Metoda ce va citi numele fisierului de iesire de la stdin
     */
    @Override
    protected void readOutputFileName(String pathImage) {
        //System.out.println("Introduce numele fisierului de iesire");
        //outputFileName = scanner.nextLine();
        //System.out.println("\n");
    	
    	int lastSeparatorIndex = pathImage.lastIndexOf("/");
    	String directory = new String();
    	
        if (lastSeparatorIndex != -1) {
        	directory = pathImage.substring(0, lastSeparatorIndex);
        	
            directory = directory + "/resize";
            directory = directory.replace("/", "\\");
        } else {
            //System.out.println("Nu s-a gasit separatorul de cale in sir.");
        }
    	
    	outputFileName = directory;
    }
}
