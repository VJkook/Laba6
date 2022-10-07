package Laba6;
import java.util.Arrays;

public class Laba6 {
    static final int size = 50_000_000;
    static final int h = size/2;
    public static void main(String[] args){
        float[] mass = new float[size];
        method1(mass);
        method2(mass);
    }

    private static void method1(float a[]) {
        Arrays.fill(a,1.0f);
        long time = System.currentTimeMillis();
        for (int i = 0; i < a.length; i++)
            a[i] = (float) (a[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        System.out.println("Время выполнения:  " + (System.currentTimeMillis() - time));
        System.out.println("Массив после: " + a[0]+" "+a[size-1]);
    }
    private static void method2(float a[]) {
        Arrays.fill(a,1.0f);
        long time = System.currentTimeMillis();
        float[] a1 = new float[h];
        float[] a2 = new float[h];
        System.arraycopy(a, 0, a1, 0, h);
        System.arraycopy(a, h, a2, 0, h);

        Thread t1=new Thread(() -> {
          for (int i = 0; i < a1.length; i++) {
              a1[i] = (float) (a1[i] * Math.sin(0.2f + (i) / 5) * Math.cos(0.2f + (i) / 5)
                      * Math.cos(0.4f + (i) / 2));}
        });
        Thread t2=new Thread(() -> {
            for (int i = 0; i < a2.length; i++){
                a2[i] = (float) (a2[i] * Math.sin(0.2f + (i+h) / 5) * Math.cos(0.2f + (i+h) / 5)
                        * Math.cos(0.4f + (i+h) / 2));}
        });
        try {
            t1.start();
            t2.start();
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.arraycopy(a1, 0, a, 0, h);
        System.arraycopy(a2, 0, a, h, h);
        System.out.println("Время выполнения: " + (System.currentTimeMillis() - time));
        System.out.println("Массив после: " + a[0]+" "+a[size-1]);
    }
}

