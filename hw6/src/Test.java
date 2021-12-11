import java.util.*;

public class Test {
    public static void main(String[]args){
        int test_num = 1;
        Node [] nodes = new Node [10];
        for(int i = 0;i<10;i++){
            nodes[i] = new Node(i,i);
        }

        switch(test_num){
            //basic test for Node
            case 0:
                for(int i = 0;i<10;i++){
                    System.out.println("(key, value) = "+ nodes[i].getKey()+","+nodes[i].getVal());
                }
                break;
                
            //test for 'isSmaller' method
            case 1:
                Node a = new Node(1,2);
                Node b = new Node(1,1);
                System.out.println(a.isSmaller(b));
                break;

            case 2:
                MinHeap h = new MinHeap();
                int i = 10;
                while(i-->-1){
                    h.add(nodes[i].getKey(), nodes[i].getKey());
                }

                ArrayList<Integer>l = h.getHeap();

        }
        
        
    }
}
