package truthtable;

public final class Syntax {

	public static void checkSyntax(StringBuilder premise) {
		int errorCode = errorCode(premise);

		if (errorCode == 1)
			throw new IllegalArgumentException("no input");
		else if (errorCode == 2)
			throw new IllegalArgumentException("empty parentheses");
		else if (errorCode == 3)
			throw new IllegalArgumentException("illegal character");
		else if (errorCode == 4)
			throw new IllegalArgumentException("illegal syntax");
		else if (errorCode == 5)
			throw new IllegalArgumentException("parentheses start not found");
		else if (errorCode == 6)
			throw new IllegalArgumentException("parentheses end not found");
	}


	public static int compType(char c) {
		if (c == '(' || c == '!')
			return 0;
		else if (c == '&' || c == 'v' || c == '>' || c == 'x')
			return 1;
		else if (c == ')')
			return 2;
		else if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z'))
			return 3;
		else if (c == '1' || c == '0')
			return 4;
		else
			return -1;
	}


	private static int errorCode(StringBuilder statement) {
		if (statement.length() == 0)
			return 1;

		char prevComp, curComp;
		int prevCompType, curCompType, startParCount;
		int[][] compTypesFSA = new int[][] { { 0, 3, 4 }, { 0, 3, 4 }, { 1, 2 }, { 1, 2 }, { 1, 2 } };

		startParCount = 0;
		curComp = statement.charAt(0);
		curCompType = compType(curComp);

		if (curCompType == -1)
			return 3;
		else if (curCompType == 1)
			return 4;
		else if (curComp == ')')
			startParCount--;
		else if (curComp == '(')
			startParCount++;
		if (startParCount < 0)
			return 5;

		for (int s = 1; s < statement.length(); s++) {
			if (statement.charAt(s) != ' ') {
				prevComp = curComp;
				prevCompType = curCompType;
				curComp = statement.charAt(s);
				curCompType = compType(curComp);

				if (prevComp == '(' && curComp == ')')
					return 2;

				if (curCompType == -1)
					return 3;
				else if (curComp == ')')
					startParCount--;
				else if (curComp == '(')
					startParCount++;
				if (startParCount < 0)
					return 5;

				int i = 0;
				while (i == compTypesFSA[prevCompType].length ? false : compTypesFSA[prevCompType][i] != curCompType)
					i++;
				if (i == compTypesFSA[prevCompType].length)
					return 4;
			}
		}

		if (startParCount > 0)
			return 6;

		return 0;
	}

}
