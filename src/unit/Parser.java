package unit;

public class Parser {
	public static void main(String[] args) {
		String sentence = "let P = 1, let Q = 0, eval P -> Q?";
		boolean value = new Parser().analyze(sentence);
		System.out.println(value);
	}

	public boolean analyze(String input) {
		return false; // TODO implement stub
	}
}