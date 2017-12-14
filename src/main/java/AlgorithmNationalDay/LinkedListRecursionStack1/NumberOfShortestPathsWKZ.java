package AlgorithmNationalDay.LinkedListRecursionStack1;

import org.apache.commons.collections.CollectionUtils;

import java.util.*;

/**
 * Created by Administrator on 2016/10/17.
 * 名称：最短路径条数
 * 备注：自己的版本(又长又慢)
 * 参考：
 */
public class NumberOfShortestPathsWKZ {
    /**
     * @Author 王坤造
     * @Date 2016/10/17 16:02
     * 名称：
     * 备注：
     */
    public static void main(String[] args) {
        long timeBegin = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            test();
        }
        long timeEnd = System.currentTimeMillis();
        System.out.println(timeEnd - timeBegin + "ms");
    }

    static void test() {
        Map<NodeShortest, NodeShortest[]> map = initNode();
        GraphShortest graph = new GraphShortest();

        graph.setRelationMap(map);
        graph.setFirstNode("A");
        graph.printNumberOfShortestPaths();
    }

    private static Map<NodeShortest, NodeShortest[]> initNode() {
        NodeShortest A = new NodeShortest("A");
        NodeShortest B = new NodeShortest("B");
        NodeShortest C = new NodeShortest("C");
        NodeShortest D = new NodeShortest("D");
        NodeShortest E = new NodeShortest("E");
        NodeShortest F = new NodeShortest("F");
        NodeShortest G = new NodeShortest("G");
        NodeShortest H = new NodeShortest("H");
        NodeShortest J = new NodeShortest("J");
        NodeShortest K = new NodeShortest("K");
        NodeShortest L = new NodeShortest("L");
        NodeShortest M = new NodeShortest("M");
        NodeShortest N = new NodeShortest("N");
        NodeShortest O = new NodeShortest("O");
        NodeShortest P = new NodeShortest("P");
        NodeShortest T = new NodeShortest("T");

        Map<NodeShortest, NodeShortest[]> map = new HashMap<>();
        map.put(A, new NodeShortest[]{B, E});
        map.put(B, new NodeShortest[]{A, C, F});
        map.put(C, new NodeShortest[]{B, D, G});
        map.put(D, new NodeShortest[]{C, H});
        map.put(E, new NodeShortest[]{A, F});
        map.put(F, new NodeShortest[]{B, E, G, K});
        map.put(G, new NodeShortest[]{C, F, H, L});
        map.put(H, new NodeShortest[]{D, G});
        map.put(J, new NodeShortest[]{K, N});
        map.put(K, new NodeShortest[]{F, J, L, O});
        map.put(L, new NodeShortest[]{G, K, M, P});
        map.put(M, new NodeShortest[]{L, T});
        map.put(N, new NodeShortest[]{J, O});
        map.put(O, new NodeShortest[]{K, N, P});
        map.put(P, new NodeShortest[]{L, O, T});
        map.put(T, new NodeShortest[]{M, P});

        return map;
    }
}

/**
 * @Author 王坤造
 * @Date 2016/10/17 18:06
 * 名称：节点类
 * 备注：
 */
class NodeShortest {
    //顶点名字
    private String name;

    public NodeShortest(String n) {
        name = n;
    }

    public String getName() {
        return name;
    }

    public void setName(String n) {
        name = n;
    }
}

/**
 * @Author 王坤造
 * @Date 2016/10/17 18:37
 * 名称：节点和初始节点的关系参数类
 * 备注：
 */
class NodeRelation {
    //起始节点
    NodeShortest firstNode = null;
    //结束节点
    NodeShortest endNode = null;
    //起始节点到结束节点的最小步数
    int numStep = 0;
    //起始节点到结束节点的最小步数总共有几种走法
    int numKind = 0;

    public NodeRelation(NodeShortest firstNode, NodeShortest endNode, int numStep, int numKind) {
        this.firstNode = firstNode;
        this.endNode = endNode;
        this.numStep = numStep;
        this.numKind = numKind;
    }

    @Override
    public String toString() {
        System.out.println("firstNode:" + firstNode.getName() +
                "\tendNode:" + endNode.getName() +
                "\tnumStep:" + numStep +
                "\tnumKind:" + numKind);
        return null;
    }
}

/**
 * @Author 王坤造
 * @Date 2016/10/17 18:06
 * 名称：图
 * 备注：
 */
class GraphShortest {
    //所有节点
    private Set<NodeShortest> nodeSet = null;
    //节点依赖关系(中心节点,环绕节点[])
    private Map<NodeShortest, NodeShortest[]> relationMap = null;
    //起始节点
    private NodeShortest firstNode = null;
    //节点和起始节点的关系参数类的集合(这里要用Map,如果用Set,在添加进重复的NodeRelation,最后计算的时候重复的它会一起计算进去)
    private Map<String, NodeRelation> nodeRelationMap = new HashMap<>();
    //用来存储未遍历的节点,顺序为:起始节点->起始节点的环绕节点->起始节点的环绕节点的环绕节点)
    private Queue<NodeShortest> queue = new ArrayDeque<NodeShortest>();

    public void setRelationMap(Map<NodeShortest, NodeShortest[]> map) {
        relationMap = map;
    }

    /**
     * @Author 王坤造
     * @Date 2016/10/18 10:44
     * 名称：根据节点名称设置起始节点,并添加到要遍历的队列中
     * 备注：
     */
    public void setFirstNode(String name) {
        Set<NodeShortest> keys = relationMap.keySet();
        for (NodeShortest key : keys) {
            if (key.getName().equals(name)) {
                firstNode = key;
                queue.add(key);
            }
        }
    }

    public void printNumberOfShortestPaths() {
        calc();
        printAllNodeRelation();
    }

    /**
     * @Author 王坤造
     * @Date 2016/10/18 15:12
     * 名称：打印输出起始节点到所有节点关系
     * 备注：
     */
    private void printAllNodeRelation() {
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        for (NodeRelation nodeRelation : nodeRelationMap.values()) {
            nodeRelation.toString();
        }
        System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
    }

    /**
     * @Author 王坤造
     * @Date 2016/10/18 15:13
     * 名称：计算所有结点和起始结点的关系
     * 备注：
     */
    public void calc() {
        if (firstNode == null) {
            System.out.println("起始节点为空");
            return;
        }

        //用来存储已经遍历过的中心节点(这个节点是遍历周围结点的中心节点)
        HashSet<NodeShortest> runedNodes = new HashSet<>();
        NodeShortest centerNode = queue.poll();
        while (centerNode != null) {
            System.out.println("当前遍历的中心节点:" + centerNode.getName());
            runedNodes.add(centerNode);

            NodeShortest[] firstNodeSides = relationMap.get(centerNode);//获取中心节点的周围节点
            //循环遍历获取 '中心节点的周围节点' 跟起始节点的关系
            for (NodeShortest node : firstNodeSides) {
                //前面判断是否'已经遍历过该节点的周围结点';后面判断是否'已经存在起始节点到该节点的关系'
                if (runedNodes.contains(node) || nodeRelationMap.keySet().contains(node.getName())) {
                    continue;
                }
                queue.add(node);

                NodeShortest[] nodeShortests = relationMap.get(node);
                //获取" '中心节点A的周围节点B' 的周围节点C",并判断"周围节点C"是否有在节点关系的集合中,有的话将"周围节点C"的节点关系存储起来
                HashSet<NodeShortest> nodeSidesExceptOne = new HashSet<>(nodeShortests.length);
                CollectionUtils.addAll(nodeSidesExceptOne,nodeShortests);
                ArrayList<NodeRelation> nodeSidesRelations = new ArrayList<>();
                for (NodeRelation nodeRelation : nodeRelationMap.values()) {
                    if (nodeSidesExceptOne.contains(nodeRelation.endNode)) {
                        nodeSidesRelations.add(nodeRelation);
                    }
                }

                //判断' "周围节点C" 的节点关系'的集合是否大于0,大于0则根据' "周围节点C" 的节点关系'去获取'中心节点A的周围节点B'的节点关系
                //用到的公式:step[j]=step[i]+1,pathN[j]=pathN[i]
                NodeRelation addNodeRelation;
                if (nodeSidesRelations.size() > 0) {
                    //先用第一个做为最小步数,然后遍历剩下集合,依次跟最小步数比大小
                    NodeRelation nodeRelation = nodeSidesRelations.get(0);
                    addNodeRelation = new NodeRelation(nodeRelation.firstNode, node, nodeRelation.numStep + 1, nodeRelation.numKind);
                    for (int i = 1; i < nodeSidesRelations.size(); i++) {
                        nodeRelation = nodeSidesRelations.get(i);
                        int numStep = nodeRelation.numStep + 1;//这里要创建个numStep的新变量,不然在nodeRelation上自增1,会影响nodeRelation
                        if (numStep < addNodeRelation.numStep) {//用较少的步数
                            addNodeRelation.numStep = numStep;
                            addNodeRelation.numKind = nodeRelation.numKind;
                        } else if (numStep == addNodeRelation.numStep) {//步数相等,则走法数目相加
                            addNodeRelation.numKind += nodeRelation.numKind;
                        }
                    }
                    nodeRelationMap.put(addNodeRelation.endNode.getName(), addNodeRelation);//使用Map,put进去才会覆盖掉以前相同键的值;如果使用Set则不行,会计算重复的值
                } else {//不大于0说明是起始节点的周围节点,此时步长为1且走法也为1
                    addNodeRelation = new NodeRelation(centerNode, node, 1, 1);
                    nodeRelationMap.put(node.getName(), addNodeRelation);//使用Map,put进去才会覆盖掉以前相同键的值;如果使用Set则不行,会计算重复的值
                }
                addNodeRelation.toString();
            }
            centerNode = queue.poll();
            System.out.println("============================================");
        }
    }
}
