package framework;

/**
 * Do not modify this class.
 */
public class Token {
	public final TokenName name;
	public final Object value;

	public Token(TokenName name) {
		this.name = name;
		this.value = null;
	}

	public Token(TokenName name, Object value) {
		this.name = name;
		this.value = value;
	}

	@Override
	public String toString() {
		if (value == null)
			return name.toString();
		else
			return name.toString() + "<" + value.toString() + ">";
	}
}