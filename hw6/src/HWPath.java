import java.util.ArrayList;

public class HWPath {
	public static void main(String[] args)  throws Exception {
		
		MinHeap pQ = new MinHeap();
		pQ.add(1, 5);
		pQ.add(11, 6);
		pQ.add(2, 6);
		pQ.add(3, 4);
		pQ.add(10, 3);
		pQ.add(9, 6);
		ArrayList<Integer> elements = pQ.getHeap();
		System.out.println("Binary MinHeap");
		for (int i=0; i<elements.size(); i++)
			System.out.printf("%5s", elements.get(i));
		System.out.println();
		
		
		// Default Constructor for PathFinder
		PathFinder pF = new PathFinder();   

		System.out.println("\n****************************");
		System.out.println("For file: sample1.txt\n");
		// Read the file
		pF.readInput("/Users/yuichihamamoto/Desktop/workplace/hw6_311/src/sample1.txt");
		
		System.out.println();
		System.out.println("Shortest path distances to all vertices\n");
		double[] sDistances = pF.shortestPathDistances(3);
		//double[] sDistances1 = pF.dist2All(3,2);
		for (int i = 0; i < sDistances.length; i++)
			System.out.println("Distance from 3 to " + i + ": " + sDistances[i]);
		System.out.println();
		
		System.out.println();
		int x = pF.noOfShortestPaths(3, 6);
		System.out.println("Number of Shortest Paths from 3 to 6: " + x + "\n");
		
		
		System.out.println("Path from source to destination using some different valuations of relaxation parameters");
		ArrayList<Integer> p1 = pF.fromSrcToDest(1, 8, 1, 0);
		System.out.println("Params (A, B): " + 1 + ", " + 0);
		if (p1 == null) System.out.println("No path exists");
		else 
			for (int i=0; i < p1.size(); i++)
				System.out.print(p1.get(i) + " ");
		System.out.println();
		
		p1 = pF.fromSrcToDest(1, 8, 0, 1);
		System.out.println("Params (A, B): " + 0 + ", " + 1);
		if (p1 == null) System.out.println("No path exists");
		else 
			for (int i=0; i < p1.size(); i++)
				System.out.print(p1.get(i) + " ");
			System.out.println();
		
		p1 = pF.fromSrcToDest(1, 8, 1, 1);
		System.out.println("Params (A, B): " + 1 + ", " + 1);
		if (p1 == null) System.out.println("No path exists");
		else 
			for (int i=0; i < p1.size(); i++)
				System.out.print(p1.get(i) + " ");
		System.out.println();
		
		System.out.println();
		System.out.println("Trip from 3 to 8 via Intersections 5, 7 and 2");
		ArrayList<Integer> inter = new ArrayList<Integer>();
		inter.add(5);
		inter.add(7);
		inter.add(2);
		p1 = pF.fromSrcToDestVia(3,  8, inter, 1, 0);
		if (p1 == null) System.out.println("No path exists");
		else 
			for (int i=0; i < p1.size(); i++)
				System.out.print(p1.get(i) + " ");
		System.out.println();
		
		System.out.println();
	
		int[] tree = pF.minCostReachabilityFromSrc(3);
		System.out.println("Minimum Cost Reachability of 3");
		for (int i=0; i<tree.length; i++)
			if (tree[i] != -1 && i != tree[i])
				System.out.println("edge between " + i + " and " + tree[i]);
		
		
		System.out.println();
		System.out.println("Cost of Reachability Tree " + pF.minCostOfReachabilityFromSrc(3) + "\n");
		
		System.out.println();
		System.out.print("Is Everything reachable from 3: ");
		System.out.println(pF.isFullReachableFromSrc(3));	
		
		
		System.out.println("\n****************************");
		System.out.println("For file: sample0.txt\n");
		// Read the file
		pF.readInput("/Users/yuichihamamoto/Desktop/workplace/hw6_311/src/sample0.txt");
		
		System.out.println("Path from source to destination using some different valuations of relaxation parameters");
		p1 = pF.fromSrcToDest(0, 3, 1, 0);
		System.out.println("Params (A, B): " + 1 + ", " + 0);
		if (p1 == null) System.out.println("No path exists");
		else 
			for (int i=0; i < p1.size(); i++)
				System.out.print(p1.get(i) + " ");
		System.out.println();
		
		System.out.println("Path from source to destination using some different valuations of relaxation parameters");
		p1 = pF.fromSrcToDest(0, 3, 1, 1);
		System.out.println("Params (A, B): " + 1 + ", " + 1);
		if (p1 == null) System.out.println("No path exists");
		else 
			for (int i=0; i < p1.size(); i++)
				System.out.print(p1.get(i) + " ");
		System.out.println();
		
		System.out.println("Path from source to destination using some different valuations of relaxation parameters");
		p1 = pF.fromSrcToDest(0, 3, 0, 1);
		System.out.println("Params (A, B): " + 0 + ", " + 1);
		if (p1 == null) System.out.println("No path exists");
		else 
			for (int i=0; i < p1.size(); i++)
				System.out.print(p1.get(i) + " ");
		System.out.println();
		
		
	}
}
