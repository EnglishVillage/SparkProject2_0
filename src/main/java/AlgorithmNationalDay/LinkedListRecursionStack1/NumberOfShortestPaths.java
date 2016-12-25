package AlgorithmNationalDay.LinkedListRecursionStack1;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Created by Administrator on 2016/10/18.
 * 名称：最短路径条数
 * 备注：老师的版本(又短又快)
 * 参考：
 */
public class NumberOfShortestPaths {

    static final int N = 16;
    static String[] arr = {"A", "B", "C", "D", "E", "F", "G", "H", "J", "K", "L", "M", "N", "O", "P", "T"};

    public static void main(String[] args) {
        long timeBegin = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            calc();
        }
        long timeEnd = System.currentTimeMillis();
        System.out.println(timeEnd-timeBegin+"ms");
    }

    static void calc(){
        int[][] G = initArray();
        int[] step = new int[N];//起始节点到i节点的最小步数
        int[] stepNum = new int[N];//起始节点到i节点的最小步数的走法

        stepNum[0] = 1;//起始节点到起始节点的最小步数为0,走法为1
        Queue<Integer> q = new ArrayDeque<Integer>();//存储要搜索的结点
        q.add(0);

        int from, i, s;

        while (!q.isEmpty()) {
            from = q.poll();//获取要遍历的中心节点
            s = step[from] + 1;//中心节点的周围节点的步长
            for (i = 1; i < N; i++) {//0是起点,不遍历
                if (G[from][i] == 1) {//连通
                    //i尚未可达或发现更快的路(权值不同才可能)
                    if ((step[i] == 0 || step[i] > s)) {
                        step[i] = s;
                        stepNum[i] = stepNum[from];
                        q.add(i);
                        //发现长度相同的路径,则走法相加
                    } else if (step[i] == s) {
                        stepNum[i] += stepNum[from];
                    }
                }
            }
        }
        print(step, stepNum);
    }

    static void print(int[] step, int[] stepNum) {
        for (int i = 0; i < step.length; i++) {
            System.out.println("当前:" + arr[i] + "\tstep:" + step[i] + "\tkind:" + stepNum[i]);
        }
        System.out.println("=============================================");
    }

    private static int[][] initArray() {
        int[][] G = new int[N][N];
        G[0][1] = G[0][4] = 1;
        G[1][5] = G[1][0] = G[1][2] = 1;
        G[2][1] = G[2][6] = G[2][3] = 1;
        G[3][2] = G[3][7] = 1;
        G[4][0] = G[4][5] = 1;
        G[5][1] = G[5][4] = G[5][6] = G[5][9] = 1;
        G[6][2] = G[6][5] = G[6][7] = G[6][10] = 1;
        G[7][3] = G[7][6] = 1;
        G[8][9] = G[8][12] = 1;
        G[9][8] = G[9][13] = G[9][10] = G[9][5] = 1;
        G[10][9] = G[10][14] = G[10][11] = G[10][6] = 1;
        G[11][10] = G[11][15] = 1;
        G[12][8] = G[12][13] = 1;
        G[13][9] = G[13][12] = G[13][14] = 1;
        G[14][10] = G[14][13] = G[14][15] = 1;
        G[15][11] = G[15][14] = 1;
        return G;
    }
}
