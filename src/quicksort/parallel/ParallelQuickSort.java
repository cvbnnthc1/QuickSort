package quicksort.parallel;

import quicksort.AbstractQuickSort;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ParallelQuickSort extends AbstractQuickSort {

    public void sort(int[] array, ForkJoinPool forkJoinPool) {
        forkJoinPool.invoke(new SortIteration(array, 0, array.length - 1));
    }

    @Override
    public void sort(int[] array, int left, int right) {
        if(left < right) {
            int t = division(array, left, right);
            SortIteration task;
            if (t - left > right - t) {
                task = new SortIteration(array, t + 1, right);
                task.fork();
                sort(array, left, t);
            } else {
                task = new SortIteration(array, left, t);
                task.fork();
                sort(array, t + 1, right);
            }
            task.join();
        }
    }

    class SortIteration extends RecursiveAction {
        private final int[] array;
        private final int left;
        private final int right;

        SortIteration(int[] array, int left, int right) {
            this.array = array;
            this.left = left;
            this.right = right;
        }

        @Override
        protected void compute() {
            if(left < right) {
                int t = division(array, left, right);
                sort(array, left, t);
                sort(array, t + 1, right);
            }
        }
    }

    public static void main(String[] args) {
        warmup();
        for (int i = 1; i <= 4; i++) {
            int[] array = generateArray(10000000, 10000000);
            ParallelQuickSort parallelQuickSort = new ParallelQuickSort();
            ForkJoinPool forkJoinPool = new ForkJoinPool(i);
            long time = System.currentTimeMillis();
            parallelQuickSort.sort(array, forkJoinPool);
            time = System.currentTimeMillis() - time;
            System.out.println(time + " on " + i + " threads");
        }
    }


    public static void warmup() {
        ParallelQuickSort parallelQuickSort = new ParallelQuickSort();
        ForkJoinPool forkJoinPool = new ForkJoinPool(2);
        for (int i = 0; i < 10000; i++) {
            parallelQuickSort.sort(generateArray(100, 100), forkJoinPool);
        }
    }
}
