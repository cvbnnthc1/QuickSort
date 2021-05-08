package quicksort;

import java.util.Random;
import java.util.stream.IntStream;

public abstract class AbstractQuickSort {
    protected int division(int[] array, int left, int right) {
        int kernelIndex = left;
        int kernel = array[kernelIndex];
        int tmp;
        for (int i = left + 1; i <= right; i++) {
            if(array[i] < kernel) {
                kernelIndex++;
                tmp = array[kernelIndex];
                array[kernelIndex] = array[i];
                array[i] = tmp;
            }
        }
        tmp = array[left];
        array[left] = array[kernelIndex];
        array[kernelIndex] = tmp;
        return kernelIndex;
    }

    abstract public void sort(int[] array, int left, int right);

    public void sort(int[] array) {
        sort(array, 0, array.length - 1);
    }

    protected static int[] generateArray(int size, int maxValue) {
        Random random = new Random();
        return IntStream.generate(() -> Math.abs(random.nextInt() % maxValue)).limit(size).toArray();
    }
}
