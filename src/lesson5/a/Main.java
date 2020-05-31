package lesson5.a;

import java.util.Arrays;

public class Main {

    static final int size = 10000000;
    static final int h = size / 2;

    public static void main(String[] args) {

        float[] arr = new float[size];


        fillArr1(arr);
        System.out.println();
        fillArr2(arr);
    }

    static void fillArr1(float[] arr) {
        Arrays.fill(arr, 1);
        long a = System.currentTimeMillis();
        calcArr(arr);
        long b = System.currentTimeMillis();

        System.out.println("Без разбивки: " + (b - a) + "мс");
        System.out.println("50-й элемент массива без разбивки:" + arr[5000000]);
    }

    static void fillArr2(float[] arr) {
        Arrays.fill(arr, 1);
        long a = System.currentTimeMillis();

        float[] a1 = new float[h];
        float[] a2 = new float[h];

        System.arraycopy(arr, 0, a1, 0, h);
        System.arraycopy(arr, h, a2, 0, h);

        Thread t1 = new Thread(() -> calcArr(a1));
        Thread t2 = new Thread(() -> calcArr(a2));

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.arraycopy(a1, 0, arr, 0, h);
        System.arraycopy(a2, 0, arr, h, h);

        long b = System.currentTimeMillis();

        System.out.println("С разбивкой, но разделкой массива вне потоков: " + (b - a) + "мс");
        System.out.println("50-й элемент массива после склеивания:" + arr[5000000]);
    }

    private static void calcArr(float[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + (float) i / 5) * Math.cos(0.2f + (float) i / 5) * Math.cos(0.4f + (float) i / 2));
        }
    }
}