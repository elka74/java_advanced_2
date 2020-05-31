package lesson5;

public class ArrayRun extends Thread {

    static final int SIZE = 10000000;
    static final int HALF = SIZE / 2;
    float[] arr = new float[SIZE];

    public ArrayRun() {
    }

    @Override
    public void run() {
        arrWhole();
        arrSplitting();

    }

    public static float[] arrayCreation(float [] arr) {
        for (int i = 0; i < SIZE; i++) {
            arr[i] = 1;
        }
        return arr;
    }

    public  void arrWhole() {
         float [] arr = new float[SIZE];
        arrayCreation (arr);
        float[] arrW = new float[SIZE];
        System.arraycopy(arr, 0, arrW, 0, SIZE );
        long a = System.currentTimeMillis();
        for (int i = 0; i < SIZE; i++) {
            arrW[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        System.currentTimeMillis();
        System.out.println("Время выполнения без разделения массива - " + (System.currentTimeMillis() - a) + " миллисекунд\n " + "arr[0]: " + arrW [0] + "\narr[5000000]:" + arrW[5000000]);
    }

    public  void arrSplitting() {
        float[] arr = new float[SIZE];
        arrayCreation (arr);
        float[] arr1 = new float[HALF];
        float[] arr2 = new float[HALF];
        long a = System.currentTimeMillis();
        System.arraycopy(arr, 0, arr1, 0, HALF );
        System.arraycopy(arr, HALF, arr2, 0, HALF);
        Thread t1 = new Thread(()->{
        for (int i = 0; i < HALF; i++) {
            arr1[i] = (float) (arr1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
    });

        /*try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        Thread t2 = new Thread(()-> {

            for (int i = 0; i < HALF; i++) {
                arr2[i] = (float) (arr2[i] * Math.sin(0.2f + (i + HALF) / 5) * Math.cos(0.2f + (i + HALF) / 5) * Math.cos(0.4f + (i + HALF) / 2));
            }
        });
        t1.start();
        t2.start();
        try {
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.arraycopy(arr1, 0, arr, 0, HALF);
        System.arraycopy(arr2, 0, arr, HALF, HALF);
        System.currentTimeMillis();
        System.out.println("Время выполнения c разделением массива - " + (System.currentTimeMillis() - a) + " миллисекунд\n " + "arr[0]: " + arr [0] + "\narr[5000000]:" + arr[5000000]);
    }
}
