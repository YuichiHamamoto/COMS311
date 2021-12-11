/**
 * @author Yuichi Hamamoto
 */
import java.io.*;
import java.util.*;

public class PathFinder {
    private int n;
    private int m;
    private Node [] intersections;
    private ArrayList<ArrayList<Node>> nei;

    public PathFinder(){}

    public void readInput(String f){
        nei = new ArrayList<>();
        File file = new File(f);
        try {
            Scanner scan = new Scanner(file);
            String[] line = scan.nextLine().trim().split("\\s+");
            n = Integer.parseInt(line[0]);
            m = Integer.parseInt(line[1]);
            intersections = new Node[n];
            
            Arrays.fill(intersections,new Node());

            for(int i = 0; i < n; i++){
                Node cur = new Node();
                line = scan.nextLine().trim().split("\\s+");
                cur.setKey(Integer.parseInt(line[0]));
                cur.setX(Integer.parseInt(line[1]));
                cur.setY(Integer.parseInt(line[2]));
                intersections[cur.getKey()] = cur;
            }

            //skip an empty line
            scan.nextLine();

            for (int i = 0; i < n; i++) {
                ArrayList<Node> temp = new ArrayList<>();
                nei.add(temp);
            }
            
            int i = 1;
            while(i < m+1){
                line = scan.nextLine().trim().split("\\s+");

                int a = Integer.parseInt(line[0]);
                int b = Integer.parseInt(line[1]);

                double weight = distance(intersections[a].getX(), intersections[a].getY(), intersections[b].getX(), intersections[b].getY());

                nei.get(a).add(new Node(b,intersections[b].getX(),intersections[b].getY(),weight));
                nei.get(b).add(new Node(a,intersections[a].getX(),intersections[a].getY(),weight));

                i++;
            }
            scan.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            e.printStackTrace();
        }
    }

    public double[] shortestPathDistances(int s){
        double dis[] = new double[n];
        HashSet<Integer> set = new HashSet<>();
        MinHeap h = new MinHeap();

        Arrays.fill(dis, -1);

        h.add(s, 0);
        dis[s] = 0;

        while(set.size()!= n){
            if(h.isEmpty()){
                return dis;
            }
            int cur = h.extractMin().getKey();
            if(set.contains(cur)){
                continue;
            }
            set.add(cur);

            double edgeD = -1;
            double newD = -1;

            for(int i = 0; i < nei.get(cur).size();i++){
                Node v =  nei.get(cur).get(i);

                if(!set.contains(v.getKey())){
                    edgeD = v.getVal();
                    newD = dis[cur] + edgeD;

                    if(dis[v.getKey()]==-1||newD<dis[v.getKey()]){
                        dis[v.getKey()] = newD;
                    }

                    h.add(v.getKey(),dis[v.getKey()]);
                }
            }
        }
        return dis;
    }

    public int noOfShortestPaths(int s, int d){
        double dis[] = new double[n];
        HashSet<Integer> set = new HashSet<>();
        MinHeap h = new MinHeap();
        int[] paths = new int[n];

        Arrays.fill(paths,0);
        Arrays.fill(dis,-1);

        h.add(s,0);
        paths[s]=1;
        dis[s]=0;

        while(set.size()!=n){
            if(h.isEmpty()){
                return paths[d];
            }

            int cur = h.extractMin().getKey();

            if(set.contains(cur)){
                continue;
            }

            set.add(cur);
            
            double edgeD = -1;
            double newD = -1;

            for(int i = 0; i < nei.get(cur).size();i++){
                Node v = nei.get(cur).get(i);
                if(!set.contains(v.getKey())){
                    edgeD = v.getVal();
                    newD = dis[cur]+edgeD;
                    if(dis[v.getKey()]==-1){
                        dis[v.getKey()]=newD;
                        paths[v.getKey()]=paths[cur];
                    }
                    else if(newD==dis[v.getKey()]){
                        paths[v.getKey()]++;
                    }

                    if(newD<dis[v.getKey()]){
                        dis[v.getKey()]= newD;
                        paths[v.getKey()] = paths[cur];
                    }

                    h.add(v.getKey(),dis[v.getKey()]);
                }
            }
        }
        return paths[d];
    }

    public ArrayList<Integer> fromSrcToDest(int sid,int did, int sar, int dar){
        double dis[] = new double[n];
        HashSet<Integer> set = new HashSet<>();
        MinHeap h = new MinHeap();
        ArrayList<Integer> list = new ArrayList<>();
        ArrayList<Integer> listp = new ArrayList<>();

        list.add(sid);

        Arrays.fill(dis,-1);

        h.add(sid, 0);
        dis[sid] = 0;

        while(set.size() < n){
            if(h.isEmpty()){
                if(list.contains(did)){
                    return list;
                }
                else{
                    return null;
                }
            }

            int cur = h.extractMin().getKey();

            if(set.contains(cur)){
                continue;
            }
            set.add(cur);

            double edgeD = -1;
            double newD = -1;

            int c = did;
            if(set.contains(did)){
                while(c != sid){
                    listp.add(c);
                    c = intersections[c].getPre();
                }
                for(int i = listp.size();i>0;i--){
                    list.add(listp.get(i-1));
                }
                if(list.contains(did)){
                    return list;
                }
                return null;
            }

            for(int i = 0; i < nei.get(cur).size();i++){
                Node v = nei.get(cur).get(i);
                if(!set.contains(v.getKey())){
                    double t = distance(intersections[v.getKey()].getX(),intersections[v.getKey()].getY() , intersections[did].getX(), intersections[did].getY()) - distance(intersections[cur].getX(),intersections[cur].getY(), intersections[did].getX(), intersections[did].getY());
                    edgeD = v.getVal();
                    newD = sar*(dis[cur]+edgeD) + dar*t;

                    if(dis[v.getKey()] == -1||newD < dis[v.getKey()]){
                        dis[v.getKey()] = newD;
                        intersections[v.getKey()].setPre(cur);
                    }
                    
                    h.add(v.getKey(), dis[v.getKey()]);
                 }
             }
        }
        if(list.contains(did)){
            return list;
        }
        return null;
    }

    public ArrayList<Integer> fromSrcToDestVia(int sid, int did, ArrayList<Integer> mid, int sar, int dar){
        ArrayList<Integer> output = new ArrayList<>();
        ArrayList<Integer> list = new ArrayList<>();
        output.add(sid);
        list.add(sid);

        for(int m: mid){
            list.add(m);
        }
        list.add(did);

        if(list.size()>=2){
            for(int i = 0; i<list.size();i++){
                ArrayList<Integer> list2 = fromSrcToDest(list.get(i), list.get(i+1), sar, dar);
                if(list2 == null){
                    return null;
                }
                for(int j = 1;j<list2.size();j++){
                    output.add(list2.get(j));
                }
                if(list.get(i+1)==did){
                    break;
                }
            }
            for(int i = 0; i < output.size();i++){
                if(output.get(i)==null){
                    return null;
                }
            }
        }
        return output;
    }

    public int[] minCostReachabilityFromSrc(int s){
        int [] mst = new int[n];
        HashSet<Integer> set = new HashSet<>();
        MinHeap h = new MinHeap();

        //initialize mst with -1
        Arrays.fill(mst, -1);

        h.add(s,0);
        mst[s]=s;

        while(!h.isEmpty()){
            Node cur = h.extractMin();
            if(set.contains(cur.getKey())){
                continue;
            }
            set.add(cur.getKey());
            for(int i = 0; i < nei.get(cur.getKey()).size();i++){
                h.add(nei.get(cur.getKey()).get(i).getKey(),nei.get(cur.getKey()).get(i).getVal());
                if(nei.get(cur.getKey()).get(i).getVal()==cur.getVal() && set.contains(nei.get(cur.getKey()).get(i).getKey())){
                    mst[cur.getKey()] = nei.get(cur.getKey()).get(i).getKey();
                }
            }
        }
        return mst;
    }

    public double minCostOfReachabilityFromSrc(int s){
        int[]mst = new int[n];
        int[]from = new int[n];
        double cost = 0;
        HashSet<Integer> set = new HashSet<>();
        MinHeap h = new MinHeap();

        //initialized mst with -1
        Arrays.fill(mst,-1);
        h.add(s, 0);

        while(!h.isEmpty()){
            Node cur = h.extractMin();
            if(set.contains(cur.getKey())){
                continue;
            }
            set.add(cur.getKey());
            for(int i = 0; i < nei.get(cur.getKey()).size();i++){
                h.add(nei.get(cur.getKey()).get(i).getKey(), nei.get(cur.getKey()).get(i).getVal());
                from[nei.get(cur.getKey()).get(i).getKey()] = cur.getKey();
            }
            for(int i = 0; i < nei.get(cur.getKey()).size();i++){
                if(nei.get(cur.getKey()).get(i).getVal()==cur.getVal() && set.contains(nei.get(cur.getKey()).get(i).getKey())){
                    cost+= nei.get(cur.getKey()).get(i).getVal();
                    break;
                }
            }
        }
        return cost;
    } 

    public boolean isFullReachableFromSrc(int s){
        int[] mst = minCostReachabilityFromSrc(s);
        for(int i = 0; i < mst.length; i++){
            if(mst[i] == -1){
                return false;
            }
        }
        return true;
    }

    public double distance(int x1, int y1, int x2, int y2) {
        return Math.sqrt((x1 - x2)* (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

}

class MinHeap{
    private ArrayList<Node> list = new ArrayList<>();

    public MinHeap(){
    }

    public void add(int key, double value){
        Node n = new Node(key, value);
        list.add(n);
        int i  = list.size()-1;
        int parent = parent(i);
        while(parent!=i&&list.get(i).isSmaller(list.get(parent))){
            swap(i, parent);
            i = parent;
            parent = parent(i);
        }
    }

    public ArrayList<Integer> getHeap(){
        ArrayList<Integer> output = new ArrayList<>();
        for(int i = 0;i<list.size();i++){
            output.add(list.get(i).getKey());
        }
        return output;
    }

    public Node extractMin() {
        if (list.size() == 0) {
            throw new IllegalStateException("MinHeap is EMPTY");
        } 
        else if (list.size() == 1) {
            Node min = list.remove(0);
            return min;
        }

        Node min = list.get(0);
        Node lastItem = list.remove(list.size() - 1);
        list.set(0, lastItem);
        minHeapify(0);
        return min;
    }

    public void minHeapify(int i) {
        int left = left(i);
        int right = right(i);
        int smallest = -1;

        if (left <= list.size() - 1 && list.get(left).isSmaller(list.get(i))) {
            smallest = left;
        } else {
            smallest = i;
        }

        if (right <= list.size() - 1 && list.get(right).isSmaller(list.get(smallest))) {
            smallest = right;
        }

        if (smallest != i) {
            swap(i, smallest);
            minHeapify(smallest);
        }
    }

    public boolean isEmpty() {
        return list.size() == 0;
    }

    public int right(int i) {
        return 2 * i + 2;
    }

    public int left(int i) {
        return 2 * i + 1;
    }
    public int parent(int i) {
        if (i % 2 == 1) {
            return i / 2;
        }
        else{
            return (i - 1) / 2;
        }
    }

    public void swap(int i, int parent) {
        Node temp = list.get(parent);
        list.set(parent, list.get(i));
        list.set(i, temp);
    }
}

class Node{
    private int key;
    private double value;
    private int x;
    private int y;
    private int pre;

    public Node(){}

    public Node(int key, double value){
        this.key = key;
        this.value = value;
    }

    public Node(int key,int x,int y, double value){
        this.key = key;
        this.value = value;
        this.x = x;
        this.y = y;
    }

    public Node(int key, double value, int pre){
        this.key = key;
        this.value = value;
        this.pre = pre;
    }

    public int getKey(){
        return key;
    }

    public double getVal(){
        return value;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public int getPre(){
        return pre;
    }
    
    public void setKey(int key){
        this.key = key;
    }

    public void setVal(double value){
        this.value = value;
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    public void setPre(int pre){
        this.pre = pre;
    }

    public boolean isSmaller(Node n){
        if(value<n.getVal()){
            return true;
        }
        else if(value==n.getVal()){
            if(key<n.getKey()){
                return true;
            }
            else{
                return false;
            }
        }
        else{
            return false;
        }
    }
}
