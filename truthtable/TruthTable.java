package truthtable;

import java.util.*;
import static truthtable.Statement.*;
import static truthtable.Syntax.*;

public class TruthTable {
	LinkedList<StringBuilder> premises;
	LinkedList<Character> vars;
	StringBuilder output;
	StringBuilder conclusion;
	int count;
	boolean outputAndConclusionAreCurrent;

	public TruthTable() {
		premises = new LinkedList();
		vars = new LinkedList();
		output = new StringBuilder();
		conclusion = new StringBuilder();
		count = 0;
		outputAndConclusionAreCurrent = true;
	}


	public void add(String statement) {
		outputAndConclusionAreCurrent = false;
		StringBuilder premise = new StringBuilder(statement.trim());
		for (int p = 0; p < premise.length(); p++)
			if (newVar(premise.charAt(p)))
				vars.add(premise.charAt(p));
		checkSyntax(premise);
		premises.add(premise);
	}


	private boolean newVar(char c) {
		if (compType(c) == 3) {
			for (char v : vars)
				if (v == c)
					return false;
			return true;
		}
		return false;
	}


	private void createTruthTableAndConclusion() {
		vars.sort(null);
		output.setLength(0);
		conclusion.setLength(0);

		createTitleLine();
		int[] binNum = new int[vars.size()];
		Arrays.fill(binNum, 0);
		do
			createNumericLine(binNum);
		while (nextBinary(binNum));

		outputAndConclusionAreCurrent = true;
	}


	private void createTitleLine() {
		output.append("Truth Table:\n| ");
		for (char c : vars)
			output.append(c).append(' ');
		output.append("| |");
		for (StringBuilder premise : premises)
			output.append(' ').append(premise).append(" ".repeat(2 - premise.length() % 2)).append("|");
		output.append('\n');

		conclusion.append("Conclusion:\n| ");
		for (char c : vars)
			conclusion.append(c).append(' ');
		conclusion.append("|\n");
	}


	private void createNumericLine(int[] binNum) {
		output.append("| ");
		for (int i : binNum)
			output.append(i).append(' ');
		output.append("| |");
		boolean allPremisesTrue = true;
		for (StringBuilder premise : premises) {
			StringBuilder statement = new StringBuilder(premise);
			for (int p = 0; p < premise.length(); p++)
				for (int v = 0; v < vars.size(); v++)
					if (premise.charAt(p) == vars.get(v))
						statement.setCharAt(p, (char) (binNum[v] + '0'));
			int solution = solution(statement) ? 1 : 0;
			allPremisesTrue &= solution == 1;
			output.append(" ".repeat(1 + premise.length() / 2)).append(solution)
					.append(" ".repeat(1 + premise.length() / 2)).append("|");
		}
		output.append('\n');

		if (allPremisesTrue) {
			conclusion.append("| ");
			for (int i : binNum)
				conclusion.append(i).append(' ');
			conclusion.append("|").append('\n');
			count++;
		}
	}


	private boolean nextBinary(int[] num) {
		int i = num.length;
		while (i-- > 0)
			if (num[i] == 0) {
				num[i] = 1;
				return true;
			} else
				num[i] = 0;
		return false;
	}


	@Override
	public String toString() {
		if (!outputAndConclusionAreCurrent)
			createTruthTableAndConclusion();
		return output.toString();
	}


	public String conclusion() {
		if (!outputAndConclusionAreCurrent)
			createTruthTableAndConclusion();
		return conclusion.toString();
	}


	public int count() {
		if (!outputAndConclusionAreCurrent)
			createTruthTableAndConclusion();
		return count;
	}

}
