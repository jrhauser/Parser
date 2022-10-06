package unit;
import java.util.HashMap;
import java.util.Queue;

import framework.Token;
import framework.TokenName;

public class Parser {
	public static void main(String[] args) {
		String sentence = "let aaa = 1, let BBB = 0, eval aaa -> BBB <-> BBB' -> aaa'?";
		boolean value = new Parser().analyze(sentence);
		System.out.println(value);
	}
	//author -  J. R. Hauser
	// I acknowledge the academic integrity policy and all code submitted is my own, or is copied directly from the resources provided
	Queue<Token> tokens;
	HashMap<String, Boolean> lookupTable = new HashMap<String,Boolean>();
	private boolean value;
	// Template method 
	public boolean analyze(String input) {
	// My own
		tokens = new Lexer().tokenize(input);
		value = program();
		expect(TokenName.END_OF_INPUT);
		return value;
	}
	//Copied from algebra example (https://ecampus.wvu.edu/webapps/blackboard/execute/blti/launchLink?course_id=_183807_1&content_id=_9970193_1)
	private boolean accept(TokenName name) {
		if (tokens.peek().name != name)
			return false;
		tokens.remove();
		return true;
	}
	//copied from algebra example (https://ecampus.wvu.edu/webapps/blackboard/execute/blti/launchLink?course_id=_183807_1&content_id=_9970193_1)
	private boolean peek(TokenName name ) {
		return tokens.peek().name == name;
	}
	//copied from algebra example (https://ecampus.wvu.edu/webapps/blackboard/execute/blti/launchLink?course_id=_183807_1&content_id=_9970193_1)
	private Object expect(TokenName name) {
		if (tokens.peek().name != name)
			throw new RuntimeException("Expected: " + name + 
			" but found: " + tokens.peek().name);
		return tokens.remove().value;
	}
	private boolean program() {
		while (peek(TokenName.LET_KEYWORD) || peek(TokenName.EVAL_KEYWORD)) {
			if (accept(TokenName.LET_KEYWORD)) {
				assignment(lookupTable);
			}
			else if (accept(TokenName.EVAL_KEYWORD))
				value = evaluation();
		}
		return value;
	}
	private boolean evaluation() {
		value = equivalance();
		expect(TokenName.QUESTION);
		return value;
	}

	private void assignment(HashMap<String, Boolean> lookup) {
		while (!peek(TokenName.COMMA)) {
			String identifier = (String) tokens.element().value;
			expect(TokenName.IDENTIFIER);
			expect(TokenName.EQUAL);
			
			value = equivalance();
		
			if (lookup.get(identifier) != null) {
					throw new RuntimeException("Variable already exists");
			} 
			
			if (lookup.size() >= 8) {
				throw new RuntimeException("The maximum number of variables has been reached");
			}  
			
			lookup.put(identifier, value);
		}
		accept(TokenName.COMMA);
	}

	private boolean equivalance() {
		value = implication();
		if (peek(TokenName.DOUBLE_ARROW)) {
			while (!(peek(TokenName.QUESTION) || peek(TokenName.CLOSE_PAREN) || peek(TokenName.COMMA))) {
				accept(TokenName.DOUBLE_ARROW);
				if (peek(TokenName.OPEN_PAREN)) {
					value = value == expression();
				} else if (peek(TokenName.BOOL_LITERAL)) {
					value = value == implication();
				} else if (peek(TokenName.IDENTIFIER)) {
					value = value == implication();
				} else {
					throw new RuntimeException("AHHH");
				}
			}
		}
		return value;
	}

	private boolean implication() {
		 value = disjunction();
		if (peek(TokenName.ARROW)) {
			while (!(peek(TokenName.QUESTION) || peek(TokenName.CLOSE_PAREN) || peek(TokenName.COMMA) )) { 
				accept(TokenName.ARROW);
				if (peek(TokenName.OPEN_PAREN)) {
					value = !value | expression();
				} else if (peek(TokenName.BOOL_LITERAL)){
					value = !value | disjunction();
				} else if (peek(TokenName.IDENTIFIER)) {
					value =  !value | disjunction();
				} else {
					equivalance();
				}
			}
		}
		return value;
	}
	
	private boolean disjunction() {
		 value = conjunction();
		if (peek(TokenName.VEE)) {
			while (!(peek(TokenName.QUESTION) || peek(TokenName.CLOSE_PAREN) || peek(TokenName.COMMA) )) {
				accept(TokenName.VEE);
				if (peek(TokenName.OPEN_PAREN)) {
					value = value | expression();
				} else if (peek(TokenName.BOOL_LITERAL)){ 
					value = value | conjunction();
				} else if (peek(TokenName.IDENTIFIER)) {
					value = value | conjunction();
				} else {
					equivalance();
				}
			}
		}
		return value;
	}
	
	private boolean conjunction() {
		 value = negation();
		if (peek(TokenName.CARET)) {
			while (!(peek(TokenName.QUESTION) || peek(TokenName.CLOSE_PAREN) || peek(TokenName.COMMA))) {
				accept(TokenName.CARET);
				if (peek(TokenName.OPEN_PAREN)) {
					value = value & expression();
				} else if (peek(TokenName.BOOL_LITERAL)){
					value = value & negation();
				} else if (peek(TokenName.IDENTIFIER)) {
					value = value & negation();
				} else {
					throw new RuntimeException("EXPECTED SOMETHING AFTER THE ^");
				}
			}
		}
		return value;
	}

	private boolean negation() {
		value = expression();
		if (peek(TokenName.APOSTROPHE)) {
			accept(TokenName.APOSTROPHE);
			value = !(value);
		}
		return value;
	}
	
	private boolean expression() {
		if (peek(TokenName.OPEN_PAREN)) {
			accept(TokenName.OPEN_PAREN);
			value = equivalance();
			expect(TokenName.CLOSE_PAREN);
		} else {
			value = myBoolean();
		}
		return value;
	}

	private boolean myBoolean() {
		if (peek(TokenName.BOOL_LITERAL)) {
			value = literal();
			return value;
		} else if (peek(TokenName.IDENTIFIER)) {
			value = lookupTable.get((String) tokens.element().value);
			accept(TokenName.IDENTIFIER);
			return value;
		}
		return program();
	}

	private boolean literal() {
		value = (Boolean) tokens.element().value;
		accept(TokenName.BOOL_LITERAL);
		return value;
	}
}