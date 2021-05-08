package quicksort.sequence;

import quicksort.AbstractQuickSort;

public class QuickSort extends AbstractQuickSort {
    @Override
    public void sort(int[] array, int left, int right) {
        if(left < right) {
            int t = division(array, left, right);
            sort(array, left, t);
            sort(array, t + 1, right);
        }
    }

    public static void main(String[] args) {
        warmup();
        int[] array = generateArray(10000000, 10000000);
        QuickSort seqSort = new QuickSort();
        long time = System.currentTimeMillis();
        seqSort.sort(array);
        time = System.currentTimeMillis() - time;
        System.out.println(time);
    }


    public static void warmup() {
        QuickSort quickSort = new QuickSort();
        for (int i = 0; i < 10000; i++) {
            quickSort.sort(generateArray(100, 100));
        }
    }
}
