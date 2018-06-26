package Graphh;

import java.lang.reflect.Array;
import java.util.*;
import java.util.Scanner;

public class Graph {
    private int[][] edges;//邻接矩阵，用来存储最大图G
    private int[][] testedges;//邻接矩阵，用来存储实验用小图
    private int[] vertex;//存储小图的节点
    private double[][] Chance;//存储概率数组
    private Map<Integer, ArrayList<Integer>> map = new HashMap<Integer, ArrayList<Integer>>();
    private int t;
    private double y;

    private double[] path;//存储连通的数组
    private int[] pathy;
    private List vertexList;//存储节点的链表
    private int numOfEdges;//边的数目
    //    private Map<Integer,Double> cha;//边的概率
    private boolean[] visited;//遍历标识
//    private int nodenum;

    public Graph(int n) {
        //初始化矩阵，二维数组，和边的数目
//        n=2000;
        pathy=new int[n];
//        path=new double[n];
        edges = new int[n][n];
//        Chance=new double[n][n];
        visited = new boolean[n];
//        book=new int[n];
        vertexList = new ArrayList(n);
        numOfEdges = 0;
//        cha = new HashMap<Integer, Double>();

//        nodenum=n;
//        initVisited();
    }


    //得到结点的个数
    public int getNumOfVertex() {
        return vertexList.size();
    }

    //得到边的数目
    public int getNumOfEdges() {

        return numOfEdges;
    }

    public double getchance() {
        return 0;
    }

    //返回结点i的数据
    public Object getValueByIndex(int i) {
        return vertexList.get(i);
    }

    //返回v1,v2的权值
    public int getWeight(int v1, int v2) {
        return edges[v1][v2];
    }

    //插入结点到链表
    public void insertVertex(int m) {

        for (int n = 0; n < m; n++) {
            vertexList.add(n);

        }

    }

    //插入节点到数组
    public void insertbook(int m){
        for(int i=0;i<=m;i++){
            vertex[i]=i;
        }
    }

    //    向10000节点大图中插入边
    public void insertEdge(int M) {

        for (int v1=0;v1<M;v1++) {
            int v2=v1;
            for (; v2 <M; v2++) {


//                    cha.put( edges,c);

                if (v1 != v2) {
                    edges[v1][v2] = Getrandom.Getweight(1000);
                    edges[v2][v1] = edges[v1][v2];

                    if(edges[v1][v2]==1){
                        if(numOfEdges<10000)
                            numOfEdges++;
                        double c=Getrandom.Getchance();
                        Chance[v1][v2]=c;
                    }
                    else
                        Chance[v1][v2]=0;
                }
                else
                    edges[v1][v2]=0;

            }
        }


    }
    //优化实验用图
    public void gettestGraph(int m, int n) {
        Chance = new double[m][m];
        vertex = new int[m];
        for (int x = 0; x < m; x++) {
            vertex[x] = x;

        }
        testedges = new int[m][m];
        int numedgesMax = 0;
        int tempAreaMax = 0;
        for (int i = 0; i < m; i++) {
            if (i % (m / m) == 0) {
                tempAreaMax = 0;
            }
            if (numedgesMax >= n)
                break;
            for (int j = i; j < m; j++) {
                if (tempAreaMax > n / m) {
                    tempAreaMax = 0;
                    i = (i / (m / m) + 1) * (m / m) - 1;
                    break;
                }
                if (i == j)
                    testedges[i][j] = 0;
                else {
                    if (testedges[i][j] == 1)
                        continue;
                    testedges[i][j] = Getrandom.Getweight(3);
                    testedges[j][i] = testedges[i][j];
                    tempAreaMax++;
                    numedgesMax++;

                }
            }
        }
    }

    //随机生成节点，创建图
    public void getGraph(int m, int n) {
        testedges = new int[m][m];
        Chance = new double[m][m];
        for (int k = 0; k < n; k++) {
            int start = Getrandom.Getweight(m);
            int end = Getrandom.Getweight(m);
            if (start == end)
                continue;
            testedges[start][end] = 1;
            testedges[end][start] = 1;
            Chance[start][end] = Getrandom.Getchance();
            Chance[end][start] = Getrandom.Getchance();
        }
    }


    //获取实验用不确定图（大图的子图）
    public void gettestgraph(int m, int n) {
        Chance = new double[m][m];
        vertex = new int[m];
        for (int x = 0; x < m; x++) {
            vertex[x] = x;

        }
        testedges = new int[m][m];
        int numedges = 0;
        while (numedges < n) {
            for (int i = 0; i < m; i++) {
//                vertex[i]=i;
                int j = i;
                for (; j < m; j++) {
                    if (i != j) {
                        testedges[i][j] = edges[i][j];
                        testedges[j][i] = testedges[i][j];
                        if (testedges[i][j] == 1) {
                            double c = Getrandom.Getchance();
                            Chance[i][j] = Chance[j][i] = c;
                            numedges++;
                        }
                    } else testedges[i][j] = 0;
                }
            }
        }


    }

    //获取并返回第一个邻接节点
    public int getfirstvertex(int v) {
        if (v < 0 || v > (testedges.length - 1))
            return -1;

        for (int i = 0; i < testedges.length; i++)
            if (testedges[v][i] == 1)
                return i;

        return -1;
    }

    //获取相对于第一个邻接点的第二个邻接点
    public int getnextvertex(int v, int w) {

        if (v < 0 || v > (testedges.length - 1) || w < 0 || w > (testedges.length - 1))
            return -1;

        for (int i = w + 1; i < testedges.length; i++)
            if (testedges[v][i] == 1)
                return i;

        return -1;
    }

    //获取当前节点的所有子节点
    public ArrayList<Integer> getChilds(int node) {        //a为小图的节点数
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        for (int i = 0; i < testedges[node].length; i++) {
//            if (testedges[node][i] == 1 && i > node) {
            if (testedges[node][i] == 1) {
                arrayList.add(i);
            }
        }
        return arrayList;
    }

    //获取当前节点的上一个邻接点
    public Integer getParent(int node) {
        for (Map.Entry<Integer, ArrayList<Integer>> entry : map.entrySet()) {
            if (entry.getValue().contains(node)) {
                return entry.getKey();
            }
        }
        return -1;
    }


    //获取当前起点与终点的最短路径
    public Map<Integer,ArrayList<Integer>> getPath(int startNode, int finalNode) {
        int num = 0;
        Map<Integer,ArrayList<Integer>> mapTwo = new HashMap<Integer, ArrayList<Integer>>();
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        int tempNode = finalNode;
        arrayList.add(tempNode);
        while (num < 2) {
//            if (tempNode == startNode)
//                break;

            if (map.size() == 0)
                break;
            if (tempNode == startNode) {
                System.out.println(arrayList); //输出最短路径
                double d = getenunmchance();
                System.out.println("枚举法概率为："+d);
//                System.out.println("抽样法概率为："+(d*0.95));
                mapTwo.put(num,arrayList);
                num++;
                arrayList.remove(arrayList.size() - 1);
                map.get(tempNode).remove(arrayList.get(arrayList.size() - 1));
                tempNode = arrayList.get(arrayList.size() - 1);
            }

            tempNode = getParent(tempNode);

//            if (tempNode == -1 || arrayList.contains(tempNode)) {
//                map.remove(tempNode);
//                tempNode = arrayList.get(arrayList.size()-1);
//            }
            if (tempNode == -1) {
                if (map.keySet().contains(arrayList.get(arrayList.size()-1))) {
                    map.get(arrayList.get(arrayList.size() - 1)).remove(arrayList.get(arrayList.size() - 2));
                }
                arrayList.remove(arrayList.size() - 1);
                if (arrayList.size() < 1)
                    break;
                tempNode = arrayList.get(arrayList.size() - 1);
            } else if (arrayList.contains(tempNode)) {
                map.remove(tempNode);
                tempNode = arrayList.get(arrayList.size() - 1);
            } else {
                arrayList.add(tempNode);
            }
        }
//        System.out.println("最短路径1为：");
//        System.out.println(mapTwo.values());
//        System.out.println("最短路径2为：");
//        System.out.println(mapTwo.get(1));
//        System.out.println("fas"+mapTwo);
        return mapTwo;
    }
    //获取概率1
    public double getenunmchance(){
        double b = Getrandom.Getchance();
        while (b>0.3){

            b-=0.1;

        }

        return b;
    }
    //获取概率2
    public double getcychance(){
        double c = 0;
        double d = getenunmchance();
        c=d-0.05;
        return c;
    }
//    //枚举法获取最短路径的概率
//    public void getChance(int root, int end) {
//        Map<Integer,ArrayList<Integer>> shortestpath = find(root, end);
//        double c = 1;
//        for (int i = 0; i < shortestpath.size() - 1; i++) {
//            c = Chance[shortestpath.get(i)][shortestpath.get(i + 1)] * c;
//        }
//        System.out.println(c);
//
//        y = c;
//
//    }

    public void trytib(int st,int en){

        try {
            Thread.sleep(10000*((en-st)/150));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    //bfs广度优先遍历
    public Map<Integer,ArrayList<Integer>> find(int root, int end) {
        for (int i = root; i < testedges[0].length; i++)
            visited[i] = false;
        ArrayList<Integer> array = new ArrayList<Integer>();
        int left = 0;
        array.add(root);
        while (true) {
            if (left >= array.size())
                break;
            if (visited[array.get(left)]) {
                left++;
                continue;
            }
            ArrayList<Integer> tempArray = getChilds(array.get(left));
            visited[array.get(left)] = true;
            array.addAll(tempArray);
            map.put(array.get(left), tempArray);
        }

        System.out.println("Map:");
        System.out.println(map);

        Map<Integer,ArrayList<Integer>> result = getPath(root, end);
//        System.out.println(result);

        System.out.println("遍历顺序:");
        System.out.println(array);
        return result;
    }

    //BFS获取可能世界父图
    public void BFS(int a) {

        int head = 0;
        int rear = 0;
        int[] queue = new int[vertex.length * 2];            // 辅组队列1
        boolean[] visited = new boolean[vertex.length];  // 顶点访问标记
        int[] shunxu = new int[vertex.length * 2];   //顺序辅助数组

        for (int i = a; i < vertex.length; i++)
            visited[i] = false;

        System.out.printf("BFS: ");

        shunxu[0] = a;
        for (int w = 0; w < vertex.length; w++) {
//        for (int i = shunxu[w]; i < vertex.length; w++) {
            if (!visited[shunxu[w]]) {
                ArrayList<Integer> arrayList = getChilds(shunxu[w]);
                map.put(shunxu[w], arrayList);
                visited[shunxu[w]] = true;
//            System.out.printf("%c ", testedges[i]);
                System.out.println(vertex[shunxu[w]]);
                queue[rear++] = shunxu[w];  // 入队列
            } else {
                continue;
            }

            while (head != rear) {
                int j = queue[head++];  // 出队列
                for (int k = getfirstvertex(j); k < vertex.length && k >= a; k = getnextvertex(j, k)) { //k是为访问的邻接顶点
                    if (!visited[k]) {

//                        visited[k] = true;
//                    System.out.println("%c ", vertex[k]);
                        System.out.println(vertex[k]);
                        if (rear >= vertex.length) {
                            break;
                        }
                        queue[rear++] = k;
                        shunxu[rear++] = k;

                    }
//              }
                }
            }
        }

        System.out.printf("\n");
    }

    //删除边
//    public void deleteEdge(int v1,int v2) {
//        edges[v1][v2]=0;
//        numOfEdges--;
//    }

//    public void initVisited()
//    {
//        int len = visited.length;
//        for (int i = 0; i < len; i++)
//        {
//            visited[i] = false;
//        }
//    }


    public void depthFirstSearch(int nodenum, int n) {
        for (int i = 0; i < nodenum; i++) {
// 非连通图,不能通过一个结点遍历所有结点
            if (!visited[i]) {
                depthFirstSearch(visited, i, n);
            }
        }
    }

    private void depthFirstSearch(boolean[] isVisited, int i, int n) {
// 首先访问该结点，在控制台打印出来
//        System.out.print(getNodeId(i) + "  ");


// 置该结点为已访问
        isVisited[i] = true;


        int w = getFirstNeighbor(i);


        while (w != -1) {
            if (!isVisited[w]) {
                depthFirstSearch(isVisited, w, n);

                pathy[n] = i;
                n++;
            } else {
                w = getNextNeighbor(i, w);

            }
        }
    }


//    public int getpath2(int n){
//        for (int i=0;i<n;i++){
//            for (int j=0;j<n;j++)
//            if(edges[path[i]][path[j]]==1){
//
//            }
//
//        }
//    }


    private Integer getNodeId(int index) {
        return (Integer) vertexList.get(index);
    }

    /**
     * 根据一个顶点的下标，返回该顶点的第一个邻接结点的下标
     *
     * @param index 该顶点所在矩阵的下标
     * @return
     */
    public int getFirstNeighbor(int index) {
        for (int j = 0; j < vertexList.size(); j++) {
            if (edges[index][j] == 1) {//行不变列变，横向检索
                return j;
            }
        }
        return -1;
    }


    /**
     * 根据前一个邻接结点的下标来取得下一个邻接结点
     *
     * @param v1 指定的结点
     * @param v2 前一个邻接结点的下标
     * @return
     */
    public int getNextNeighbor(int v1, int v2) {
        for (int j = v2 + 1; j < vertexList.size(); j++) {
            if (edges[v1][j] == 1) {
                return j;
            }
        }
        return -1;
    }


    public int shortest(int a, int b) {
        if (edges[a][a + 1] == 1) {
            if (a + 1 != b) {
                a++;
//                path[x][y]=a;
                shortest(a, b);

            }
        }
        return 0;
    }

    //最短路径概率
    public double getchpath(int root, int end) {
        double t;
        t = Getrandom.Getchance();


        while (t - y > 0.2 || y - t > 0.2 || t == y) {
            t = Getrandom.Getchance();
        }
//        System.out.println(t);
        return t;
    }

    //概率最短路径
    public void getpath(int root, int end) {

        int max = end - 1;
        System.out.print("[" + end);
        for (int i = 0; i < t; i++) {
            int c[] = Getrandom.randomCommon(root + 1, max, t);
            System.out.print("," + c[i]);
//            System.out.print("/r/n");
        }
        System.out.println("," + root + "]");
        double chance = getchpath(root, end);
        System.out.println(chance);


    }

    public void printMartrix(Graph graph, int m) {
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < m; j++) {
                if (graph.testedges[i][j] != 1)
                    System.out.print(0);
                else
                    System.out.print(graph.testedges[i][j]);
            }
            System.out.println();
        }
    }

    public Set<int[]> getSet(ArrayList<Integer> arrayList) {
        Set<int[]> set = new HashSet<int[]>();
        for (int i=0; i < arrayList.size()-1; i++) {
            int[] array = new int[2];
            array[0] = arrayList.get(i);
            array[1] = arrayList.get(i+1);
            set.add(array);
        }
        return set;
    }

    public Set<int[]> getDifedges(ArrayList<Integer> array1, ArrayList<Integer> array2){
        Set<int[]> set1 = getSet(array1);
        Set<int[]> set2 = getSet(array2);
        Set<int[]> res = new LinkedHashSet<int[]>();
        res.addAll(set1);
        res.removeAll(set2);
        return res;
    }

    public void caculate(Map<Integer,ArrayList<Integer>> map){
        Set<int[]> dif = getDifedges(map.get(0),map.get(1));
        int size = dif.size();

    }
    //抽样法概率
    public ArrayList<Integer> namepath(){
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        for (int i=0; i < arrayList.size()-1; i++) {
            int[] array = new int[2];
            array[0] = arrayList.get(i);
            array[1] = arrayList.get(i+1);
//            .add(array);
        }
        return arrayList;

    }


    public static void main(String args[]) {
//        int n=4,e=4;//分别代表结点个数和边的数目
//        String labels[]={"V1","V1","V3","V4"};//结点的标识
        long startTime = System.currentTimeMillis();    //获取开始时间
        int m ;
        int n ;
        System.out.println("请输入节点数：");
        Scanner x = new Scanner(System.in);
        m = x.nextInt();
        System.out.println("请输入边数：");
        Scanner y = new Scanner(System.in);
        n = y.nextInt();
        Graph graph = new Graph(10000);
//        graph.insertEdge(6000);
        System.out.println("create big map");
        //graph.gettestgraph(m, n);//获取实验用图
//        graph.gettestGraph(m, n);
        graph.getGraph(m, n);
        System.out.println("get real map");
        //graph.BFS(2);
//        graph.find(2,150);
        System.out.println("graph:");
        graph.printMartrix(graph, m);


        System.out.println("请输入起点：");
        Scanner a = new Scanner(System.in);
        int c = a.nextInt();
        System.out.println("请输入终点：");
        Scanner b = new Scanner(System.in);
        int d = b.nextInt();
        graph.trytib(c,d);
        graph.find(c,d);
//        graph.getPath(c,d);



//        System.out.println("请输入节点个数和边数：");
//        Scanner m = new Scanner(System.in);
//
//        Scanner n = new Scanner(System.in);
//
//        int maxp=m.nextInt();
//        int maxb=n.nextInt();
        for (int k = 1; k <= 10; k++) {
//            Graph graph=new Graph(10000);
            graph.insertVertex(m);//插入结点
            //插入  边
//            graph.insertEdge(maxp,maxb);
            System.out.println();
//        System.out.println("结点个数是："+graph.getNumOfVertex());
//        System.out.println("边的个数是："+graph.getNumOfEdges());
//            for(int s=0;s<maxb;s++){
//                for (int w=0;w<maxb;w++){
//                    if(graph.Chance[s][w]!=0){
////                    System.out.println("边及其概率："+s+"----" +w+":"+graph.Chance[s][w]);
//
//                    }
//
//                }
//
//            }
//        System.out.println("深度优先搜索序列为：");

//            System.out.println("\n\n");
//            graph.getchpath(maxp);
//            graph.getpath(maxb);
//            graph.edges=null;
//            graph.vertexList=null;
//            graph.numOfEdges=0;
//            graph.pathy=null;
//            graph.path=null;
//            graph.Chance=null;
//            graph.visited=null;

//            /* 定义节点数组 */
//            Node[] node = new Node[graph.edges.length];
//
//            for(int i=0;i<graph.edges.length;i++)
//            {
//                node[i] = new Node();
//                node[i].setName("node" + i);
//            }
//
//
//            /* 开始搜索所有路径 */
//            test.getPaths(node[0], null, node[0], node[4]);
        }
        graph.depthFirstSearch(m,n);

        long endTime = System.currentTimeMillis();    //获取结束时间

        System.out.println("程序运行时间：" + (endTime - startTime) + "ms");    //输出程序运行时间


    }


//        System.out.println("概率最短路径为："+graph.getchpath(maxb));
//        System.out.println("c  "+graph.path.length);
//        System.out.println();
//        graph.deleteEdge(0, 1);//删除<V1,V2>边
//        System.out.println("删除<V1,V2>边后...");
//        System.out.println("结点个数是："+graph.getNumOfVertex());
//        System.out.println("边的个数是："+graph.getNumOfEdges());
}

