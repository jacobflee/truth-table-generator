package truthtable;

public class Component {

	public Component next;

	public char op;
	public boolean val;

	public Component(char comp) {
		val = comp == '1';
		op = val || comp == '0' ? '_' : comp;
	}

}
