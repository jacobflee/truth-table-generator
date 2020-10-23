package truthtable;

import java.util.*;

public class Main {

	public static void main(String[] args) {
		TruthTable table = new TruthTable();
		// table.add("(!p > (s & q)) x (!q > r)");
		// table.add("r v s");
		// table.add("q > !p");
		// table.add("s > !(q & r)");
		// table.add("(r v p) x (r & p)");
		table.add("0 > p");
		System.out.println(table.toString());
		System.out.println(table.conclusion());
		System.out.println(table.count());
	}
}
