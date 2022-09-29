package unit;

import java.util.LinkedList;
import java.util.Queue;


import framework.Token;
import framework.TokenName;

public class Lexer {
	public static void main(String[] args) {
		String sentence = "^vEVAL letEVAL ' V Pv & ^";
		Queue<Token> tokens = new Lexer().tokenize(sentence);
		while (!tokens.isEmpty())
			System.out.println(tokens.remove());
	}

	public Queue<Token> tokenize(String input) {
		Queue<Token> tokens = new LinkedList<Token>();

		 int unexpected = -1;
		for (int index = 0; index < input.length(); index++) {
			if (Character.isWhitespace(input.charAt(index)))  {
				continue;
			}
			if (input.charAt(index) == ',') {
				tokens.add(new Token(TokenName.COMMA));
			}
			else if (input.charAt(index) == '^') {
				tokens.add(new Token(TokenName.CARET));
			}
			else if (input.charAt(index) == '(') {
				tokens.add(new Token(TokenName.OPEN_PAREN));
			}
			else if (input.charAt(index) == ')') {
				tokens.add(new Token(TokenName.CLOSE_PAREN));
			}
			else if (input.charAt(index) == '?') {
				tokens.add(new Token(TokenName.QUESTION));
			}
			else if (input.charAt(index) == '=') {
				tokens.add(new Token(TokenName.EQUAL));
			}
			else if (input.charAt(index) == '\'') {
				tokens.add(new Token(TokenName.APOSTROPHE));
			}
			else if (input.charAt(index) == '-') {
				if (index + 1 < input.length() && input.charAt(index + 1) == '>' ) {
					index += 1;
					tokens.add(new Token(TokenName.ARROW));
				}
				else {
					tokens.add(new Token(TokenName.UNEXPECTED_INPUT));
					unexpected += 1;
					break;
				}			
			}
			else if (input.charAt(index) == '<') { 
				if (index + 2 < input.length() && input.charAt(index + 1) == '-' && input.charAt(index + 2) == '>') {
					index += 2;
					tokens.add(new Token(TokenName.DOUBLE_ARROW));
				}
				else {
					tokens.add(new Token(TokenName.UNEXPECTED_INPUT));
					unexpected += 1;
					break;
				}
			}
			else if (input.charAt(index) == '1') {
				boolean value = true;
				tokens.add(new Token(TokenName.BOOL_LITERAL, value));
			}
			else if (input.charAt(index) == '0') {
				boolean value = false;
				tokens.add(new Token(TokenName.BOOL_LITERAL, value));
			}
			else if (Character.isLetter(input.charAt(index))) {
				int len = 1;
				while (
					index + len < input.length() &&
					Character.isLetter(input.charAt(index + len))
				) len++;
				String lexeme = input.substring(index, index+len);
				if (len == 1 && lexeme.equalsIgnoreCase("v")) {
					tokens.add(new Token(TokenName.VEE));
				} else if (lexeme.equalsIgnoreCase("let")) {
					tokens.add(new Token(TokenName.LET_KEYWORD));
					index += len-1;
				} else if (lexeme.equalsIgnoreCase("eval")) {
					index += len-1;
					tokens.add(new Token(TokenName.EVAL_KEYWORD));
				} else {
					tokens.add(new Token(TokenName.IDENTIFIER, lexeme));
					index += len-1;
				}
			}
			else {
				tokens.add(new Token(TokenName.UNEXPECTED_INPUT));
				unexpected += 1;
				break;
			}
		}
		if (unexpected == -1){
			tokens.add(new Token(TokenName.END_OF_INPUT));
		}
		return tokens;
	}
}