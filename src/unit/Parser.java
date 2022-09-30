package unit;
import java.util.HashMap;
import java.util.Queue;
import framework.Token;
import framework.TokenName;

public class Parser {
	public static void main(String[] args) {
		String sentence = "eval 1?";
		boolean value = new Parser().analyze(sentence);
		System.out.println(value);
	}

	Queue<Token> tokens;
	HashMap<Token, Boolean> lookup;

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
		accept(TokenName.EVAL_KEYWORD);
		boolean value = equivalance();
		accept(TokenName.QUESTION);
		return value;
	}

	private boolean assignment() {
		expect(TokenName.IDENTIFIER);
		boolean value = (Boolean) tokens.element().value;
		lookup.put(tokens.element(), value);
		return value;
	}

	private boolean equivalance() {
		boolean value = implication();
		return value;
	}

	private boolean implication() {
		boolean value = disjunction();
		return value;
	}
	
	private boolean disjunction() {
		boolean value = conjunction();
		return value;
	}
	
	private boolean conjunction() {
		boolean value = negation();
		return value;
	}

	private boolean negation() {
		boolean value = expression();
		return value;
	}
	
	private boolean expression() {
		boolean value = boolean();
		return value;
	}

	private boolean myBoolean() { 
		boolean value = literal();
		return value;
	}

	private boolean literal() {
		boolean value = tokens.element().value;
		return value;
	}
}