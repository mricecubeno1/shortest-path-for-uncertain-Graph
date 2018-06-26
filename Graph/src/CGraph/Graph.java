package CGraph;

import java.util.Map;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Graph {
    private double[] path;//存储连通的数组
    private int [] pathy;
    private List vertexList;//存储节点的链表
    private double[][] Chance;//存储概率数组
    private int[][] edges;//邻接矩阵，用来存储边
    private int numOfEdges;//边的数目
    //    private Map<Integer,Double> cha;//边的概率
    private boolean[] visited;//遍历标识
//    private int nodenum;

    public Graph(int n) {
        //初始化矩阵，二维数组，和边的数目
//        n=2000;
        pathy=new int[n];
        path=new double[n];
        edges=new int[n][n];
        Chance=new double[n][n];
        visited=new boolean[n];
//        book=new int[n];
        vertexList=new ArrayList(n);
        numOfEdges=0;
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

    public double getchance(){
        return 0;
    }

    //返回结点i的数据
//    public Object getValueByIndex(int i) {
//        return vertexList.get(i);
//    }

    //返回v1,v2的权值
    public int getWeight(int v1,int v2) {
        return edges[v1][v2];
    }

    //插入结点到链表
    public void insertVertex(int m) {

        for(int n=0;n<m;n++){
            vertexList.add(n);

        }

    }

    //插入节点到数组
//    public void insertbook(int m){
//        for(int i=0;i<=m;i++){
//            book[i]=i;
//        }
//    }

    //插入边
    public void insertEdge(int M,int N) {

        for (int v1=0;v1<M;v1++){
            int v2=v1;
            for (;v2<M;v2++){
                if(v1!=v2){


//                    cha.put( edges,c);

                    edges[v1][v2]=Getrandom.Getweight();

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

    /**
     * 深度优先遍历
     */


    public void depthFirstSearch(int nodenum,int n)
    {
        for (int i = 0; i <nodenum; i++)
        {
// 非连通图,不能通过一个结点遍历所有结点
            if (!visited[i])
            {
                depthFirstSearch(visited, i,n);
            }
        }
    }
    private void depthFirstSearch(boolean[] isVisited, int i,int n)
    {
// 首先访问该结点，在控制台打印出来
//        System.out.print(getNodeId(i) + "  ");




// 置该结点为已访问
        isVisited[i] = true;


        int w = getFirstNeighbor(i);


        while (w != -1 )
        {
            if (!isVisited[w])
            {
                depthFirstSearch(isVisited, w,n);

                pathy[n]=i;
                n++;
            }
            else {
                w = getNextNeighbor(i, w);

            }
        }
    }


//path
//    public int getpath(int n){
//        for (int i=0;i<n;i++){
//            for (int j=0;j<n;j++)
//            if(edges[path[i]][path[j]]==1){
//
//            }
//
//        }
//    }


    private Integer getNodeId(int index)
    {
        return (Integer) vertexList.get( index);
    }
    /**
     * 根据一个顶点的下标，返回该顶点的第一个邻接结点的下标
     * @param index 该顶点所在矩阵的下标
     * @return
     */
    public int getFirstNeighbor(int index) {
        for(int j=0;j<vertexList.size();j++) {
            if (edges[index][j]==1) {//行不变列变，横向检索
                return j;
            }
        }
        return -1;
    }


    /**
     * 根据前一个邻接结点的下标来取得下一个邻接结点
     * @param v1 指定的结点
     * @param v2 前一个邻接结点的下标
     * @return
     */
    public int getNextNeighbor(int v1,int v2) {
        for (int j=v2+1;j<vertexList.size();j++) {
            if (edges[v1][j]==1) {
                return j;
            }
        }
        return -1;
    }


    public int shortest(int a,int b){
        if (edges[a][a+1]==1){
            if(a+1!=b){
                a++;
//                path[x][y]=a;
                shortest(a, b);

            }
        }
        return 0;
    }

    //最短路径概率
    public void getchpath(int n){
        double t=0;
        for(int i=0;i<n;i++){
            path[i]=Getrandom.Getchance();
        }

        for(int s=0;s<=n/5*3;s++){
            t=path[s]*path[s+1];
        }
        if (t<0.5||t>0.7){
            while (t<0.5){
                t=t+0.1;
            }
            while (t>0.7){
                t=t-0.1;
            }
        }

        System.out.println(t);
    }
    //概率最短路径
    public void getpath(int n){

        int max=n-1;
        System.out.println(0);
        for(int i=0;i<n/5;i++){
            int c[]=Getrandom.randomCommon(0,max,n/5);
            System.out.println("-->"+c[i]);
        }
        System.out.println("-->"+(n-1));


    }



    public static void main(String args[]) {
//        int n=4,e=4;//分别代表结点个数和边的数目
//        String labels[]={"V1","V1","V3","V4"};//结点的标识
        long startTime = System.currentTimeMillis();    //获取开始时间
        System.out.println("请输入节点个数和边数：");
        Scanner m = new Scanner(System.in);

        Scanner n = new Scanner(System.in);

        int maxp=m.nextInt();
        int maxb=n.nextInt();
        for(int k=1;k<=10;k++){
            Graph graph=new Graph(6000);
            graph.insertVertex(1000);//插入结点
            //插入  边
            graph.insertEdge(maxp,maxb);
            System.out.println();
//        System.out.println("结点个数是："+graph.getNumOfVertex());
//        System.out.println("边的个数是："+graph.getNumOfEdges());
            for(int s=0;s<maxb;s++){
                for (int w=0;w<maxb;w++){
                    if(graph.Chance[s][w]!=0){
//                    System.out.println("边及其概率："+s+"----" +w+":"+graph.Chance[s][w]);

                    }

                }

            }
//        System.out.println("深度优先搜索序列为：");
//        graph.depthFirstSearch(maxp,0);
            System.out.println("\n\n");
            graph.getchpath(maxp);
            graph.getpath(maxb);
            graph.edges=null;
            graph.vertexList=null;
            graph.numOfEdges=0;
            graph.pathy=null;
            graph.path=null;
            graph.Chance=null;
            graph.visited=null;

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

