package truthtable;

public final class Statement {

	public static boolean solution(StringBuilder statement) {
		Component root = new Component(statement.charAt(0));
		Component cur = root;

		for (int i = 1; i < statement.length(); i++)
			if (statement.charAt(i) != ' ') {
				cur.next = new Component(statement.charAt(i));
				cur = cur.next;
			}

		return solution(root, null);
	}


	private static boolean solution(Component start, Component end) {
		Component cur;

		cur = start;
		while (cur != end)
			if (cur.op == '(') {
				Component endComp = getEndComp(cur);
				cur.val = solution(cur.next, endComp);
				cur.op = '_';
				cur.next = endComp.next;
			} else
				cur = cur.next;

		cur = start;
		while (cur != end)
			if (cur.op == '!') {
				cur.val = !cur.next.val;
				cur.op = '_';
				cur.next = cur.next.next;
			} else
				cur = cur.next;

		cur = start;
		while (cur.next != end)
			if (cur.next.op != '_') {
				if (cur.next.op == '&')
					cur.val = cur.val && cur.next.next.val;
				else if (cur.next.op == 'v')
					cur.val = cur.val || cur.next.next.val;
				else if (cur.next.op == '>')
					cur.val = !cur.val || cur.next.next.val;
				else // (cur.next.op == 'x') xor
					cur.val = cur.val != cur.next.next.val;

				cur.next = cur.next.next.next;
			} else
				cur = cur.next;

		return cur.val;
	}


	private static Component getEndComp(Component cur) {
		Component end = cur.next;
		int startParCount = 0;

		while (startParCount != 0 || end.op != ')') {
			if (end.op == '(')
				startParCount++;
			else if (end.op == ')')
				startParCount--;
			end = end.next;
		}

		return end;
	}

}
