package backend;

import java.awt.image.BufferedImage;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

public class ResizeTask implements Runnable {
    private final int newX;
    private final int newY;

    private final int currentState;

    private final double scale;

    private final ExecutorService executorService;
    private final CountDownLatch countDownLatch;

    private final ImageProcessor imageProcessor;

    public ResizeTask(int newX, int newY, int currentState, ExecutorService executorService, CountDownLatch countDownLatch, ImageProcessor imageProcessor, double scale) {
        this.newX = newX;
        this.newY = newY;
        this.currentState = currentState;
        this.executorService = executorService;
        this.countDownLatch = countDownLatch;
        this.imageProcessor = imageProcessor;
        this.scale = scale;
    }

    @Override
    public void run() {
        BufferedImage inputImage = imageProcessor.inputImage;
        BufferedImage outputImage = imageProcessor.outputImage;

        //System.out.printf("Au fost primite datele pentru etapa %s\n", currentState);

        // Aflam portiunea de date din imagine care trebuie prelucrata in task-ul din pipeline
        int start = length(newY, currentState - 1);
        int end = length(newY, currentState);

        // Incepem contorizarea timpului
        //Long startTime = System.currentTimeMillis();
        for (int y = start; y < end; y++) {
            for (int x = 0; x < newX; x++) {
                int originalY = (int)(y * ((double)inputImage.getHeight() / scale / newY));
                int originalX = (int)(x * ((double)inputImage.getWidth() / scale / newX));
                try {
                    int pixel = inputImage.getRGB(originalX, originalY);
                    outputImage.setRGB(x, y, pixel);
                }
                // Chiar daca este aruncata atunci doar cand dam zoom out
                // Este necesara pentru a observa efectul de zoom out
                catch (ArrayIndexOutOfBoundsException e) {
                    continue;
                }
            }
        }
        // Se scrie imaginea din etapa curenta intr-un fisier
        imageProcessor.save(String.format("%s_%s.bmp", imageProcessor.getOutputFileName(), currentState));
        // Oprim contorizarea timpului
        //Long endTime = System.currentTimeMillis();
        //System.out.printf("Etapa %s s-a terminat si a durat %s ms\n\n", currentState, endTime - startTime);

        // Asteptam o secunda pentru a trece la urmatoarea etapa
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Semnalam elementul de sincronizare ca trecem la urmatoarea etapa
        countDownLatch.countDown();

        // Pana cand nu ma voi afla in stagiul 4 al pipeline-ului, voi lansa un task nou pentru procesarea urmatorului sfert de imagine
        if (currentState != Scalable.fourthQuarter) {
            executorService.submit(
                    new ResizeTask(
                            newX,
                            newY,
                            currentState + 1,
                            executorService,
                            countDownLatch,
                            imageProcessor,
                            scale
                    )
            );
        }
    }

    private int length(int newCoordinate, int epoch) {
        switch (epoch) {
            case Scalable.start: return 0;
            case Scalable.firstQuarter: return newCoordinate / 4;
            case Scalable.secondQuarter: return newCoordinate / 2;
            case Scalable.thirdQuarter: return 3 * newCoordinate / 4;
            case Scalable.fourthQuarter: return newCoordinate;
            default: throw new IllegalArgumentException("Wrong end");
        }
    }
}
