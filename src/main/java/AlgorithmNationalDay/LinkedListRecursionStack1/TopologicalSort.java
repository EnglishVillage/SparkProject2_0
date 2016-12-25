package AlgorithmNationalDay.LinkedListRecursionStack1;

import java.util.*;

/**
 * Created by Administrator on 2016/10/14.
 * 拓扑排序[充要条件就是它是一个有向无环图(DAG：Directed Acyclic Graph)]
 * 参考链接:http://blog.csdn.net/kimylrong/article/details/17220455
 */
public class TopologicalSort {
    public static void main(String[] args) {
        //数据源
        Set<VertexWKZ> vertexSet = new HashSet<VertexWKZ>();//所有节点
        Map<VertexWKZ, VertexWKZ[]> edgeMap = new HashMap<VertexWKZ, VertexWKZ[]>();//节点依赖关系
        addVertex2(vertexSet, edgeMap);

        GraphDAG graphDAG = new GraphDAG();
        graphDAG.setVertexSet(vertexSet);
        graphDAG.setAdjacencys(edgeMap);
        graphDAG.wkzPrintVertex();

        vertexSet.clear();edgeMap.clear();
        addVertex1(vertexSet, edgeMap);
        graphDAG.setVertexSet(vertexSet);
        graphDAG.setAdjacencys(edgeMap);
        graphDAG.wkzPrintVertex();
    }

    private static void addVertex2(Set<VertexWKZ> vertexSet, Map<VertexWKZ, VertexWKZ[]> edgeMap) {
        VertexWKZ zeroVertex = new VertexWKZ("0");
        VertexWKZ oneVertex = new VertexWKZ("1");
        VertexWKZ twoVertex = new VertexWKZ("2");
        VertexWKZ threeVertex = new VertexWKZ("3");
        VertexWKZ fourVertex = new VertexWKZ("4");
        VertexWKZ fiveVertex = new VertexWKZ("5");
        VertexWKZ sixVertex = new VertexWKZ("6");
        VertexWKZ sevenVertex = new VertexWKZ("7");
        VertexWKZ eightVertex = new VertexWKZ("8");
        VertexWKZ nineVertex = new VertexWKZ("9");
        VertexWKZ tenVertex = new VertexWKZ("10");
        VertexWKZ elevenVertex = new VertexWKZ("11");
        VertexWKZ twelveVertex = new VertexWKZ("12");

        vertexSet.add(zeroVertex);
        vertexSet.add(oneVertex);
        vertexSet.add(twoVertex);
        vertexSet.add(threeVertex);
        vertexSet.add(fourVertex);
        vertexSet.add(fiveVertex);
        vertexSet.add(sixVertex);
        vertexSet.add(sevenVertex);
        vertexSet.add(eightVertex);
        vertexSet.add(nineVertex);
        vertexSet.add(tenVertex);
        vertexSet.add(elevenVertex);
        vertexSet.add(twelveVertex);


        edgeMap.put(zeroVertex, new VertexWKZ[]{oneVertex, fiveVertex, sixVertex});
        edgeMap.put(twoVertex, new VertexWKZ[]{zeroVertex, threeVertex});
        edgeMap.put(threeVertex, new VertexWKZ[]{fiveVertex});
        edgeMap.put(fiveVertex, new VertexWKZ[]{fourVertex});
        edgeMap.put(sixVertex, new VertexWKZ[]{fourVertex, nineVertex});
        edgeMap.put(sevenVertex, new VertexWKZ[]{sixVertex});
        edgeMap.put(eightVertex, new VertexWKZ[]{sevenVertex});
        edgeMap.put(nineVertex, new VertexWKZ[]{tenVertex, elevenVertex, twelveVertex});
        edgeMap.put(elevenVertex, new VertexWKZ[]{twelveVertex});
    }

    private static void addVertex1(Set<VertexWKZ> vertexSet, Map<VertexWKZ, VertexWKZ[]> edgeMap) {
        VertexWKZ twoVertex = new VertexWKZ("2");
        VertexWKZ threeVertex = new VertexWKZ("3");
        VertexWKZ fiveVertex = new VertexWKZ("5");
        VertexWKZ sevenVertex = new VertexWKZ("7");
        VertexWKZ eightVertex = new VertexWKZ("8");
        VertexWKZ nineVertex = new VertexWKZ("9");
        VertexWKZ tenVertex = new VertexWKZ("10");
        VertexWKZ elevenVertex = new VertexWKZ("11");

        vertexSet.add(twoVertex);
        vertexSet.add(threeVertex);
        vertexSet.add(fiveVertex);
        vertexSet.add(sevenVertex);
        vertexSet.add(eightVertex);
        vertexSet.add(nineVertex);
        vertexSet.add(tenVertex);
        vertexSet.add(elevenVertex);

        edgeMap.put(twoVertex, new VertexWKZ[]{elevenVertex});
        edgeMap.put(nineVertex, new VertexWKZ[]{elevenVertex, eightVertex});
        edgeMap.put(tenVertex, new VertexWKZ[]{elevenVertex, threeVertex});
        edgeMap.put(elevenVertex, new VertexWKZ[]{sevenVertex, fiveVertex});
        edgeMap.put(eightVertex, new VertexWKZ[]{sevenVertex, threeVertex});
    }
}

/**
 * 顶点类
 */
class VertexWKZ {
    //顶点名字
    private String name;

    public VertexWKZ(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

/**
 * 有向图
 */
class GraphDAG {
    //所有节点
    private Set<VertexWKZ> vertexSet = null;
    //节点依赖关系(输入节点,输出节点[])
    private Map<VertexWKZ, VertexWKZ[]> adjacencys = null;
    //入度为0的节点集合
    private Set<VertexWKZ> indegreeZero = new HashSet<>();
    //入度不为0的(节点,入度)字典
    private Map<VertexWKZ, Integer> indegreeOther = new HashMap<VertexWKZ, Integer>();

    public void setVertexSet(Set<VertexWKZ> set) {
        vertexSet = set;
        calcIndegree();
    }

    public void setAdjacencys(Map<VertexWKZ, VertexWKZ[]> map) {
        adjacencys = map;
        calcIndegree();
    }

    /**
     * 根据所有节点和节点依赖关系计算入度
     */
    private void calcIndegree() {
        if (vertexSet != null && adjacencys != null) {
            indegreeZero.clear();
            indegreeOther.clear();
            //获取入度大于0的节点
            Collection<VertexWKZ[]> values = adjacencys.values();
            for (VertexWKZ[] vers : values) {
                for (VertexWKZ ver : vers) {
                    if (indegreeOther.containsKey(ver)) {
                        indegreeOther.put(ver, indegreeOther.get(ver) + 1);
                    } else {
                        indegreeOther.put(ver, 1);
                    }
                }
            }
            //获取入度等于0的节点
            for (VertexWKZ ver : vertexSet) {
                if (!indegreeOther.containsKey(ver)) {
                    indegreeZero.add(ver);
                }
            }
        }
    }

    /**
     * 打印输出拓扑排序后结果(里面先拓扑排序再输出)
     */
    public void wkzPrintVertex() {
        List<VertexWKZ> list = wkzTopologicalSort();
        StringBuilder sb = new StringBuilder();
        for (VertexWKZ vertex : list) {
            sb.append(vertex.getName() + "\t");
        }
        System.out.println(sb);
    }

    /**
     * 拓扑排序
     */
    public List<VertexWKZ> wkzTopologicalSort() {
        ArrayList<VertexWKZ> list = new ArrayList<>(vertexSet.size());
        Queue<VertexWKZ> queue = new ArrayDeque<VertexWKZ>();
        //先获取入度为0的节点
        if (indegreeZero != null && indegreeZero.size() > 0) {
            for (VertexWKZ ver : indegreeZero) {
                queue.add(ver);
                list.add(ver);
            }
        }

        while (queue.size() > 0) {
            VertexWKZ poll = queue.poll();
            VertexWKZ[] vertices = adjacencys.get(poll);
            if (vertices != null && vertices.length > 0) {
                int newIndegree;
                //将"'前节点为 入度为0的节点'的入度"减1,再重新判断入度为0的节点,并添加到队列中
                for (VertexWKZ ver : vertices) {
                    newIndegree = indegreeOther.get(ver) - 1;
                    indegreeOther.put(ver, newIndegree);
                    if (newIndegree == 0) {
                        queue.add(ver);
                        list.add(ver);
                    }
                }
            }
        }

        return list;
    }
}



