package Algorithm.SortingAlgorithm;

/**
 * 直接插入排序
 */
public class ZhiJieChaRu {
    public static void main(String[] args) {
        int[] a = {49, 38, 65, 97, 76, 13, 27, 49, 78, 34, 12, 64, 1};
        int[] a2 = {49, 38, 65, 97, 176, 213, 227, 49, 78, 34, 12, 164, 11, 18, 1};// 二分插入排序

        int[] arr = a;

        System.out.println("排序之前：");
        printArr(arr);

        fun5(arr);

        System.out.println();
        System.out.println("排序之后：");
        printArr(arr);
    }

    private static void printArr(int[] a) {
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + " ");
        }
    }

    // ZhiJieChaRu
    private static void fun(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            // 待插入元素
            int temp = arr[i];
            int j;
            for (j = i - 1; j >= 0; j--) {
                // 将大于temp的往后移动一位
                if (arr[j] > temp) {
                    arr[j + 1] = arr[j];
                } else {
                    break;
                }
            }
            arr[j + 1] = temp;
            System.out.println();
            printArr(arr);
            System.out.println();
        }
    }

    // 二分插入排序
    private static void fun2(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            int temp = arr[i];
            int left = 0;
            int right = i - 1;
            int mid = 0;
            while (left <= right) {
                mid = (left + right) / 2;
                if (temp < arr[mid]) {
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
            for (int j = i - 1; j >= left; j--) {
                arr[j + 1] = arr[j];
            }
            if (left != i) {
                arr[left] = temp;
            }
        }
    }

    // 希尔排序
    private static void fun3(int[] arr) {
        int d = arr.length;
        while (true) {
            d = d / 2;
            for (int x = 0; x < d; x++) {
                for (int i = x + d; i < arr.length; i = i + d) {
                    int temp = arr[i];
                    int j;
                    for (j = i - d; j >= 0 && arr[j] > temp; j = j - d) {
                        arr[j + d] = arr[j];
                    }
                    arr[j + d] = temp;
                }
            }
            if (d == 1) {
                break;
            }
        }
    }

    // （简单选择）/（直接）排序
    private static void fun4(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            int min = arr[i];
            int n = i; // 最小数的索引
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < min) { // 找出最小的数
                    min = arr[j];
                    n = j;
                }
            }
            arr[n] = arr[i];
            arr[i] = min;
        }
    }

    // 冒泡排序
    private static void fun5(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                // 这里-i主要是每遍历一次都把最大的i个数沉到最底下去了，没有必要再替换了
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
    }

}
