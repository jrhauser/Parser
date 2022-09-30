package unit;
import java.util.HashMap;
import java.util.Queue;
import framework.Token;
import framework.TokenName;

public class Parser {
	public static void main(String[] args) {
		String sentence = "let P = 1, let Q = 0, eval P -> Q?";
		boolean value = new Parser().analyze(sentence);
		System.out.println(value);
	}

	Queue<Token> tokens;
	
	public boolean analyze(String input) {
		tokens = new Lexer().tokenize(input);
		
		boolean value = program();
		expect(TokenName.END_OF_INPUT);
		
		return value;
	}

	private boolean accept(TokenName name) {
		if (tokens.peek().name != name)
			return false;
		
		tokens.remove();
		return true;
	}
	
	private boolean peek(TokenName name ) {
		return tokens.peek().name == name;
	}

	private Object expect(TokenName name) {
		if (tokens.peek().name != name)
			throw new RuntimeException("Expected: " + name + 
			"but found: " + tokens.peek().name);
		
			return tokens.remove().value;
	}
	
	private boolean program() {
		HashMap<Token, Boolean> lookup;
		boolean value = evaluation();
		while (peek(TokenName.LET_KEYWORD) || peek(TokenName.EVAL_KEYWORD)) {
			if (accept(TokenName.LET_KEYWORD))
				value = assignment();
			else if (accept(TokenName.EVAL_KEYWORD))
				value = equivalance();
		}

		return value;
	}

	private boolean evaluation() {
		if (peek(TokenName.EVAL_KEYWORD)) {
			
		}
		boolean value = equivalance();

		return value;
	}

	private boolean assignment() {
		
		return false;
	}

	private boolean equivalance() {
		return false;
	}

}