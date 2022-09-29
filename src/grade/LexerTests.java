package grade;

import static framework.TokenName.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.LinkedList;
import java.util.Queue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import framework.Token;
import framework.TokenName;
import unit.Lexer;

/**
 * Do not modify this class.
 */
@RunWith(Parameterized.class)
public class LexerTests {
	@Parameters(name="{index}: {0}")
	public static Object[][] data() {
		return new Object[][] {
			{ "eval 1?", tokens(EVAL_KEYWORD, withValue(BOOL_LITERAL, true), QUESTION, END_OF_INPUT ) },
			{ "eval 0?", tokens(EVAL_KEYWORD, withValue(BOOL_LITERAL, false), QUESTION, END_OF_INPUT ) },
			{ "eval 1'?", tokens(EVAL_KEYWORD, withValue(BOOL_LITERAL, true), APOSTROPHE, QUESTION, END_OF_INPUT ) },
			{ "eval 0'?", tokens(EVAL_KEYWORD, withValue(BOOL_LITERAL, false), APOSTROPHE, QUESTION, END_OF_INPUT ) },

			{ "eval 1 -> 1?", tokens(EVAL_KEYWORD, withValue(BOOL_LITERAL, true), ARROW, withValue(BOOL_LITERAL, true), QUESTION, END_OF_INPUT ) },
			{ "eval 1 -> 0?", tokens(EVAL_KEYWORD, withValue(BOOL_LITERAL, true), ARROW, withValue(BOOL_LITERAL, false), QUESTION, END_OF_INPUT ) },
			{ "eval 0 -> 1?", tokens(EVAL_KEYWORD, withValue(BOOL_LITERAL, false), ARROW, withValue(BOOL_LITERAL, true), QUESTION, END_OF_INPUT ) },
			{ "eval 0 -> 0?", tokens(EVAL_KEYWORD, withValue(BOOL_LITERAL, false), ARROW, withValue(BOOL_LITERAL, false), QUESTION, END_OF_INPUT ) },

			{ "eval 1 <-> 1?", tokens(EVAL_KEYWORD, withValue(BOOL_LITERAL, true), DOUBLE_ARROW, withValue(BOOL_LITERAL, true), QUESTION, END_OF_INPUT ) },
			{ "eval 1 <-> 0?", tokens(EVAL_KEYWORD, withValue(BOOL_LITERAL, true), DOUBLE_ARROW, withValue(BOOL_LITERAL, false), QUESTION, END_OF_INPUT ) },
			{ "eval 0 <-> 1?", tokens(EVAL_KEYWORD, withValue(BOOL_LITERAL, false), DOUBLE_ARROW, withValue(BOOL_LITERAL, true), QUESTION, END_OF_INPUT ) },
			{ "eval 0 <-> 0?", tokens(EVAL_KEYWORD, withValue(BOOL_LITERAL, false), DOUBLE_ARROW, withValue(BOOL_LITERAL, false), QUESTION, END_OF_INPUT ) },

			{ "let P = 1, let Q = 1, eval P ^ Q?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "P"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "Q"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, EVAL_KEYWORD, withValue(IDENTIFIER, "P"), CARET, withValue(IDENTIFIER, "Q"), QUESTION, END_OF_INPUT ) },
			{ "let P = 1, let Q = 0, eval P ^ Q?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "P"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "Q"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, EVAL_KEYWORD, withValue(IDENTIFIER, "P"), CARET, withValue(IDENTIFIER, "Q"), QUESTION, END_OF_INPUT ) },
			{ "let P = 0, let Q = 1, eval P ^ Q?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "P"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "Q"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, EVAL_KEYWORD, withValue(IDENTIFIER, "P"), CARET, withValue(IDENTIFIER, "Q"), QUESTION, END_OF_INPUT ) },
			{ "let P = 0, let Q = 0, eval P ^ Q?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "P"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "Q"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, EVAL_KEYWORD, withValue(IDENTIFIER, "P"), CARET, withValue(IDENTIFIER, "Q"), QUESTION, END_OF_INPUT ) },

			{ "let P = 1, let Q = 1, let R = 1, eval P ^ Q ^ R?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "P"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "Q"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "R"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, EVAL_KEYWORD, withValue(IDENTIFIER, "P"), CARET, withValue(IDENTIFIER, "Q"), CARET, withValue(IDENTIFIER, "R"), QUESTION, END_OF_INPUT ) },
			{ "let P = 1, let Q = 1, let R = 0, eval P ^ Q ^ R?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "P"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "Q"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "R"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, EVAL_KEYWORD, withValue(IDENTIFIER, "P"), CARET, withValue(IDENTIFIER, "Q"), CARET, withValue(IDENTIFIER, "R"), QUESTION, END_OF_INPUT ) },
			{ "let P = 1, let Q = 0, let R = 1, eval P ^ Q ^ R?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "P"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "Q"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "R"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, EVAL_KEYWORD, withValue(IDENTIFIER, "P"), CARET, withValue(IDENTIFIER, "Q"), CARET, withValue(IDENTIFIER, "R"), QUESTION, END_OF_INPUT ) },
			{ "let P = 1, let Q = 0, let R = 0, eval P ^ Q ^ R?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "P"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "Q"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "R"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, EVAL_KEYWORD, withValue(IDENTIFIER, "P"), CARET, withValue(IDENTIFIER, "Q"), CARET, withValue(IDENTIFIER, "R"), QUESTION, END_OF_INPUT ) },
			{ "let P = 0, let Q = 1, let R = 1, eval P ^ Q ^ R?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "P"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "Q"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "R"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, EVAL_KEYWORD, withValue(IDENTIFIER, "P"), CARET, withValue(IDENTIFIER, "Q"), CARET, withValue(IDENTIFIER, "R"), QUESTION, END_OF_INPUT ) },
			{ "let P = 0, let Q = 1, let R = 0, eval P ^ Q ^ R?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "P"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "Q"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "R"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, EVAL_KEYWORD, withValue(IDENTIFIER, "P"), CARET, withValue(IDENTIFIER, "Q"), CARET, withValue(IDENTIFIER, "R"), QUESTION, END_OF_INPUT ) },
			{ "let P = 0, let Q = 0, let R = 1, eval P ^ Q ^ R?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "P"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "Q"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "R"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, EVAL_KEYWORD, withValue(IDENTIFIER, "P"), CARET, withValue(IDENTIFIER, "Q"), CARET, withValue(IDENTIFIER, "R"), QUESTION, END_OF_INPUT ) },
			{ "let P = 0, let Q = 0, let R = 0, eval P ^ Q ^ R?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "P"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "Q"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "R"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, EVAL_KEYWORD, withValue(IDENTIFIER, "P"), CARET, withValue(IDENTIFIER, "Q"), CARET, withValue(IDENTIFIER, "R"), QUESTION, END_OF_INPUT ) },

			{ "let A = 1, let B = 1, eval A v B?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "A"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "B"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, EVAL_KEYWORD, withValue(IDENTIFIER, "A"), VEE, withValue(IDENTIFIER, "B"), QUESTION, END_OF_INPUT ) },
			{ "let A = 1, let B = 0, eval A v B?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "A"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "B"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, EVAL_KEYWORD, withValue(IDENTIFIER, "A"), VEE, withValue(IDENTIFIER, "B"), QUESTION, END_OF_INPUT ) },
			{ "let A = 0, let B = 1, eval A v B?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "A"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "B"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, EVAL_KEYWORD, withValue(IDENTIFIER, "A"), VEE, withValue(IDENTIFIER, "B"), QUESTION, END_OF_INPUT ) },
			{ "let A = 0, let B = 0, eval A v B?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "A"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "B"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, EVAL_KEYWORD, withValue(IDENTIFIER, "A"), VEE, withValue(IDENTIFIER, "B"), QUESTION, END_OF_INPUT ) },

			{ "let A = 1, let B = 1, let C = 1, eval A v B v C?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "A"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "B"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "C"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, EVAL_KEYWORD, withValue(IDENTIFIER, "A"), VEE, withValue(IDENTIFIER, "B"), VEE, withValue(IDENTIFIER, "C"), QUESTION, END_OF_INPUT ) },
			{ "let A = 1, let B = 1, let C = 0, eval A v B v C?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "A"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "B"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "C"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, EVAL_KEYWORD, withValue(IDENTIFIER, "A"), VEE, withValue(IDENTIFIER, "B"), VEE, withValue(IDENTIFIER, "C"), QUESTION, END_OF_INPUT ) },
			{ "let A = 1, let B = 0, let C = 1, eval A v B v C?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "A"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "B"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "C"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, EVAL_KEYWORD, withValue(IDENTIFIER, "A"), VEE, withValue(IDENTIFIER, "B"), VEE, withValue(IDENTIFIER, "C"), QUESTION, END_OF_INPUT ) },
			{ "let A = 1, let B = 0, let C = 0, eval A v B v C?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "A"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "B"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "C"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, EVAL_KEYWORD, withValue(IDENTIFIER, "A"), VEE, withValue(IDENTIFIER, "B"), VEE, withValue(IDENTIFIER, "C"), QUESTION, END_OF_INPUT ) },
			{ "let A = 0, let B = 1, let C = 1, eval A v B v C?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "A"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "B"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "C"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, EVAL_KEYWORD, withValue(IDENTIFIER, "A"), VEE, withValue(IDENTIFIER, "B"), VEE, withValue(IDENTIFIER, "C"), QUESTION, END_OF_INPUT ) },
			{ "let A = 0, let B = 1, let C = 0, eval A v B v C?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "A"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "B"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "C"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, EVAL_KEYWORD, withValue(IDENTIFIER, "A"), VEE, withValue(IDENTIFIER, "B"), VEE, withValue(IDENTIFIER, "C"), QUESTION, END_OF_INPUT ) },
			{ "let A = 0, let B = 0, let C = 1, eval A v B v C?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "A"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "B"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "C"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, EVAL_KEYWORD, withValue(IDENTIFIER, "A"), VEE, withValue(IDENTIFIER, "B"), VEE, withValue(IDENTIFIER, "C"), QUESTION, END_OF_INPUT ) },
			{ "let A = 0, let B = 0, let C = 0, eval A v B v C?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "A"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "B"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "C"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, EVAL_KEYWORD, withValue(IDENTIFIER, "A"), VEE, withValue(IDENTIFIER, "B"), VEE, withValue(IDENTIFIER, "C"), QUESTION, END_OF_INPUT ) },

			{ "let X = 1, let Y = 1, eval X -> Y?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "X"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "Y"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, EVAL_KEYWORD, withValue(IDENTIFIER, "X"), ARROW, withValue(IDENTIFIER, "Y"), QUESTION, END_OF_INPUT ) },
			{ "let X = 1, let Y = 0, eval X -> Y?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "X"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "Y"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, EVAL_KEYWORD, withValue(IDENTIFIER, "X"), ARROW, withValue(IDENTIFIER, "Y"), QUESTION, END_OF_INPUT ) },
			{ "let X = 0, let Y = 1, eval X -> Y?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "X"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "Y"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, EVAL_KEYWORD, withValue(IDENTIFIER, "X"), ARROW, withValue(IDENTIFIER, "Y"), QUESTION, END_OF_INPUT ) },
			{ "let X = 0, let Y = 0, eval X -> Y?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "X"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "Y"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, EVAL_KEYWORD, withValue(IDENTIFIER, "X"), ARROW, withValue(IDENTIFIER, "Y"), QUESTION, END_OF_INPUT ) },

			{ "let X = 1, let Y = 1, let Z = 1, eval X -> Y -> Z?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "X"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "Y"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "Z"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, EVAL_KEYWORD, withValue(IDENTIFIER, "X"), ARROW, withValue(IDENTIFIER, "Y"), ARROW, withValue(IDENTIFIER, "Z"), QUESTION, END_OF_INPUT ) },
			{ "let X = 1, let Y = 1, let Z = 0, eval X -> Y -> Z?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "X"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "Y"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "Z"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, EVAL_KEYWORD, withValue(IDENTIFIER, "X"), ARROW, withValue(IDENTIFIER, "Y"), ARROW, withValue(IDENTIFIER, "Z"), QUESTION, END_OF_INPUT ) },
			{ "let X = 1, let Y = 0, let Z = 1, eval X -> Y -> Z?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "X"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "Y"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "Z"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, EVAL_KEYWORD, withValue(IDENTIFIER, "X"), ARROW, withValue(IDENTIFIER, "Y"), ARROW, withValue(IDENTIFIER, "Z"), QUESTION, END_OF_INPUT ) },
			{ "let X = 1, let Y = 0, let Z = 0, eval X -> Y -> Z?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "X"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "Y"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "Z"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, EVAL_KEYWORD, withValue(IDENTIFIER, "X"), ARROW, withValue(IDENTIFIER, "Y"), ARROW, withValue(IDENTIFIER, "Z"), QUESTION, END_OF_INPUT ) },
			{ "let X = 0, let Y = 1, let Z = 1, eval X -> Y -> Z?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "X"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "Y"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "Z"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, EVAL_KEYWORD, withValue(IDENTIFIER, "X"), ARROW, withValue(IDENTIFIER, "Y"), ARROW, withValue(IDENTIFIER, "Z"), QUESTION, END_OF_INPUT ) },
			{ "let X = 0, let Y = 1, let Z = 0, eval X -> Y -> Z?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "X"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "Y"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "Z"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, EVAL_KEYWORD, withValue(IDENTIFIER, "X"), ARROW, withValue(IDENTIFIER, "Y"), ARROW, withValue(IDENTIFIER, "Z"), QUESTION, END_OF_INPUT ) },
			{ "let X = 0, let Y = 0, let Z = 1, eval X -> Y -> Z?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "X"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "Y"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "Z"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, EVAL_KEYWORD, withValue(IDENTIFIER, "X"), ARROW, withValue(IDENTIFIER, "Y"), ARROW, withValue(IDENTIFIER, "Z"), QUESTION, END_OF_INPUT ) },
			{ "let X = 0, let Y = 0, let Z = 0, eval X -> Y -> Z?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "X"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "Y"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "Z"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, EVAL_KEYWORD, withValue(IDENTIFIER, "X"), ARROW, withValue(IDENTIFIER, "Y"), ARROW, withValue(IDENTIFIER, "Z"), QUESTION, END_OF_INPUT ) },

			{ "let D = 1, let E = 1, eval D <-> E?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "D"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "E"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, EVAL_KEYWORD, withValue(IDENTIFIER, "D"), DOUBLE_ARROW, withValue(IDENTIFIER, "E"), QUESTION, END_OF_INPUT ) },
			{ "let D = 1, let E = 0, eval D <-> E?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "D"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "E"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, EVAL_KEYWORD, withValue(IDENTIFIER, "D"), DOUBLE_ARROW, withValue(IDENTIFIER, "E"), QUESTION, END_OF_INPUT ) },
			{ "let D = 0, let E = 1, eval D <-> E?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "D"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "E"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, EVAL_KEYWORD, withValue(IDENTIFIER, "D"), DOUBLE_ARROW, withValue(IDENTIFIER, "E"), QUESTION, END_OF_INPUT ) },
			{ "let D = 0, let E = 0, eval D <-> E?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "D"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "E"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, EVAL_KEYWORD, withValue(IDENTIFIER, "D"), DOUBLE_ARROW, withValue(IDENTIFIER, "E"), QUESTION, END_OF_INPUT ) },

			{ "let D = 1, let E = 1, let F = 1, eval D <-> E <-> F?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "D"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "E"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "F"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, EVAL_KEYWORD, withValue(IDENTIFIER, "D"), DOUBLE_ARROW, withValue(IDENTIFIER, "E"), DOUBLE_ARROW, withValue(IDENTIFIER, "F"), QUESTION, END_OF_INPUT ) },
			{ "let D = 1, let E = 1, let F = 0, eval D <-> E <-> F?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "D"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "E"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "F"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, EVAL_KEYWORD, withValue(IDENTIFIER, "D"), DOUBLE_ARROW, withValue(IDENTIFIER, "E"), DOUBLE_ARROW, withValue(IDENTIFIER, "F"), QUESTION, END_OF_INPUT ) },
			{ "let D = 1, let E = 0, let F = 1, eval D <-> E <-> F?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "D"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "E"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "F"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, EVAL_KEYWORD, withValue(IDENTIFIER, "D"), DOUBLE_ARROW, withValue(IDENTIFIER, "E"), DOUBLE_ARROW, withValue(IDENTIFIER, "F"), QUESTION, END_OF_INPUT ) },
			{ "let D = 1, let E = 0, let F = 0, eval D <-> E <-> F?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "D"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "E"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "F"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, EVAL_KEYWORD, withValue(IDENTIFIER, "D"), DOUBLE_ARROW, withValue(IDENTIFIER, "E"), DOUBLE_ARROW, withValue(IDENTIFIER, "F"), QUESTION, END_OF_INPUT ) },
			{ "let D = 0, let E = 1, let F = 1, eval D <-> E <-> F?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "D"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "E"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "F"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, EVAL_KEYWORD, withValue(IDENTIFIER, "D"), DOUBLE_ARROW, withValue(IDENTIFIER, "E"), DOUBLE_ARROW, withValue(IDENTIFIER, "F"), QUESTION, END_OF_INPUT ) },
			{ "let D = 0, let E = 1, let F = 0, eval D <-> E <-> F?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "D"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "E"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "F"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, EVAL_KEYWORD, withValue(IDENTIFIER, "D"), DOUBLE_ARROW, withValue(IDENTIFIER, "E"), DOUBLE_ARROW, withValue(IDENTIFIER, "F"), QUESTION, END_OF_INPUT ) },
			{ "let D = 0, let E = 0, let F = 1, eval D <-> E <-> F?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "D"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "E"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "F"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, EVAL_KEYWORD, withValue(IDENTIFIER, "D"), DOUBLE_ARROW, withValue(IDENTIFIER, "E"), DOUBLE_ARROW, withValue(IDENTIFIER, "F"), QUESTION, END_OF_INPUT ) },
			{ "let D = 0, let E = 0, let F = 0, eval D <-> E <-> F?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "D"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "E"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "F"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, EVAL_KEYWORD, withValue(IDENTIFIER, "D"), DOUBLE_ARROW, withValue(IDENTIFIER, "E"), DOUBLE_ARROW, withValue(IDENTIFIER, "F"), QUESTION, END_OF_INPUT ) },

			{ "let W = 1, eval W?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "W"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, EVAL_KEYWORD, withValue(IDENTIFIER, "W"), QUESTION, END_OF_INPUT ) },
			{ "let W = 0, eval W?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "W"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, EVAL_KEYWORD, withValue(IDENTIFIER, "W"), QUESTION, END_OF_INPUT ) },
			{ "let W = 1, eval W'?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "W"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, EVAL_KEYWORD, withValue(IDENTIFIER, "W"), APOSTROPHE, QUESTION, END_OF_INPUT ) },
			{ "let W = 0, eval (W')'?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "W"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, EVAL_KEYWORD, OPEN_PAREN, withValue(IDENTIFIER, "W"), APOSTROPHE, CLOSE_PAREN, APOSTROPHE, QUESTION, END_OF_INPUT ) },
			{ "let W = 1, eval (W)?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "W"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, EVAL_KEYWORD, OPEN_PAREN, withValue(IDENTIFIER, "W"), CLOSE_PAREN, QUESTION, END_OF_INPUT ) },
			{ "let W = 0, eval ((W))?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "W"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, EVAL_KEYWORD, OPEN_PAREN, OPEN_PAREN, withValue(IDENTIFIER, "W"), CLOSE_PAREN, CLOSE_PAREN, QUESTION, END_OF_INPUT ) },

			{ "let AB = 1, let Q = 1, eval (AB ^ Q)' <-> (AB' v Q')?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "AB"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "Q"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, EVAL_KEYWORD, OPEN_PAREN, withValue(IDENTIFIER, "AB"), CARET, withValue(IDENTIFIER, "Q"), CLOSE_PAREN, APOSTROPHE, DOUBLE_ARROW, OPEN_PAREN, withValue(IDENTIFIER, "AB"), APOSTROPHE, VEE, withValue(IDENTIFIER, "Q"), APOSTROPHE, CLOSE_PAREN, QUESTION, END_OF_INPUT ) },
			{ "let AB = 1, let Q = 0, eval (AB ^ Q)' <-> (AB' v Q')?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "AB"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "Q"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, EVAL_KEYWORD, OPEN_PAREN, withValue(IDENTIFIER, "AB"), CARET, withValue(IDENTIFIER, "Q"), CLOSE_PAREN, APOSTROPHE, DOUBLE_ARROW, OPEN_PAREN, withValue(IDENTIFIER, "AB"), APOSTROPHE, VEE, withValue(IDENTIFIER, "Q"), APOSTROPHE, CLOSE_PAREN, QUESTION, END_OF_INPUT ) },
			{ "let AB = 0, let Q = 1, eval (AB ^ Q)' <-> (AB' v Q')?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "AB"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "Q"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, EVAL_KEYWORD, OPEN_PAREN, withValue(IDENTIFIER, "AB"), CARET, withValue(IDENTIFIER, "Q"), CLOSE_PAREN, APOSTROPHE, DOUBLE_ARROW, OPEN_PAREN, withValue(IDENTIFIER, "AB"), APOSTROPHE, VEE, withValue(IDENTIFIER, "Q"), APOSTROPHE, CLOSE_PAREN, QUESTION, END_OF_INPUT ) },
			{ "let AB = 0, let Q = 0, eval (AB ^ Q)' <-> (AB' v Q')?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "AB"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "Q"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, EVAL_KEYWORD, OPEN_PAREN, withValue(IDENTIFIER, "AB"), CARET, withValue(IDENTIFIER, "Q"), CLOSE_PAREN, APOSTROPHE, DOUBLE_ARROW, OPEN_PAREN, withValue(IDENTIFIER, "AB"), APOSTROPHE, VEE, withValue(IDENTIFIER, "Q"), APOSTROPHE, CLOSE_PAREN, QUESTION, END_OF_INPUT ) },

			{ "let P = 1, let CD = 1, eval (P v CD)' <-> (P' ^ CD')?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "P"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "CD"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, EVAL_KEYWORD, OPEN_PAREN, withValue(IDENTIFIER, "P"), VEE, withValue(IDENTIFIER, "CD"), CLOSE_PAREN, APOSTROPHE, DOUBLE_ARROW, OPEN_PAREN, withValue(IDENTIFIER, "P"), APOSTROPHE, CARET, withValue(IDENTIFIER, "CD"), APOSTROPHE, CLOSE_PAREN, QUESTION, END_OF_INPUT ) },
			{ "let P = 1, let CD = 0, eval (P v CD)' <-> (P' ^ CD')?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "P"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "CD"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, EVAL_KEYWORD, OPEN_PAREN, withValue(IDENTIFIER, "P"), VEE, withValue(IDENTIFIER, "CD"), CLOSE_PAREN, APOSTROPHE, DOUBLE_ARROW, OPEN_PAREN, withValue(IDENTIFIER, "P"), APOSTROPHE, CARET, withValue(IDENTIFIER, "CD"), APOSTROPHE, CLOSE_PAREN, QUESTION, END_OF_INPUT ) },
			{ "let P = 0, let CD = 1, eval (P v CD)' <-> (P' ^ CD')?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "P"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "CD"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, EVAL_KEYWORD, OPEN_PAREN, withValue(IDENTIFIER, "P"), VEE, withValue(IDENTIFIER, "CD"), CLOSE_PAREN, APOSTROPHE, DOUBLE_ARROW, OPEN_PAREN, withValue(IDENTIFIER, "P"), APOSTROPHE, CARET, withValue(IDENTIFIER, "CD"), APOSTROPHE, CLOSE_PAREN, QUESTION, END_OF_INPUT ) },
			{ "let P = 0, let CD = 0, eval (P v CD)' <-> (P' ^ CD')?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "P"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "CD"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, EVAL_KEYWORD, OPEN_PAREN, withValue(IDENTIFIER, "P"), VEE, withValue(IDENTIFIER, "CD"), CLOSE_PAREN, APOSTROPHE, DOUBLE_ARROW, OPEN_PAREN, withValue(IDENTIFIER, "P"), APOSTROPHE, CARET, withValue(IDENTIFIER, "CD"), APOSTROPHE, CLOSE_PAREN, QUESTION, END_OF_INPUT ) },

			{ "let JKL = 1, let Q = 1, eval (JKL -> Q) <-> (JKL' v Q)?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "JKL"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "Q"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, EVAL_KEYWORD, OPEN_PAREN, withValue(IDENTIFIER, "JKL"), ARROW, withValue(IDENTIFIER, "Q"), CLOSE_PAREN, DOUBLE_ARROW, OPEN_PAREN, withValue(IDENTIFIER, "JKL"), APOSTROPHE, VEE, withValue(IDENTIFIER, "Q"), CLOSE_PAREN, QUESTION, END_OF_INPUT ) },
			{ "let JKL = 1, let Q = 0, eval (JKL -> Q) <-> (JKL' v Q)?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "JKL"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "Q"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, EVAL_KEYWORD, OPEN_PAREN, withValue(IDENTIFIER, "JKL"), ARROW, withValue(IDENTIFIER, "Q"), CLOSE_PAREN, DOUBLE_ARROW, OPEN_PAREN, withValue(IDENTIFIER, "JKL"), APOSTROPHE, VEE, withValue(IDENTIFIER, "Q"), CLOSE_PAREN, QUESTION, END_OF_INPUT ) },
			{ "let JKL = 0, let Q = 1, eval (JKL -> Q) <-> (JKL' v Q)?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "JKL"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "Q"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, EVAL_KEYWORD, OPEN_PAREN, withValue(IDENTIFIER, "JKL"), ARROW, withValue(IDENTIFIER, "Q"), CLOSE_PAREN, DOUBLE_ARROW, OPEN_PAREN, withValue(IDENTIFIER, "JKL"), APOSTROPHE, VEE, withValue(IDENTIFIER, "Q"), CLOSE_PAREN, QUESTION, END_OF_INPUT ) },
			{ "let JKL = 0, let Q = 0, eval (JKL -> Q) <-> (JKL' v Q)?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "JKL"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "Q"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, EVAL_KEYWORD, OPEN_PAREN, withValue(IDENTIFIER, "JKL"), ARROW, withValue(IDENTIFIER, "Q"), CLOSE_PAREN, DOUBLE_ARROW, OPEN_PAREN, withValue(IDENTIFIER, "JKL"), APOSTROPHE, VEE, withValue(IDENTIFIER, "Q"), CLOSE_PAREN, QUESTION, END_OF_INPUT ) },

			{ "let P = 1, let MNO = 1, eval P -> MNO <-> P' v MNO?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "P"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "MNO"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, EVAL_KEYWORD, withValue(IDENTIFIER, "P"), ARROW, withValue(IDENTIFIER, "MNO"), DOUBLE_ARROW, withValue(IDENTIFIER, "P"), APOSTROPHE, VEE, withValue(IDENTIFIER, "MNO"), QUESTION, END_OF_INPUT ) },
			{ "let P = 1, let MNO = 0, eval P -> MNO <-> P' v MNO?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "P"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "MNO"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, EVAL_KEYWORD, withValue(IDENTIFIER, "P"), ARROW, withValue(IDENTIFIER, "MNO"), DOUBLE_ARROW, withValue(IDENTIFIER, "P"), APOSTROPHE, VEE, withValue(IDENTIFIER, "MNO"), QUESTION, END_OF_INPUT ) },
			{ "let P = 0, let MNO = 1, eval P -> MNO <-> P' v MNO?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "P"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "MNO"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, EVAL_KEYWORD, withValue(IDENTIFIER, "P"), ARROW, withValue(IDENTIFIER, "MNO"), DOUBLE_ARROW, withValue(IDENTIFIER, "P"), APOSTROPHE, VEE, withValue(IDENTIFIER, "MNO"), QUESTION, END_OF_INPUT ) },
			{ "let P = 0, let MNO = 0, eval P -> MNO <-> P' v MNO?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "P"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "MNO"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, EVAL_KEYWORD, withValue(IDENTIFIER, "P"), ARROW, withValue(IDENTIFIER, "MNO"), DOUBLE_ARROW, withValue(IDENTIFIER, "P"), APOSTROPHE, VEE, withValue(IDENTIFIER, "MNO"), QUESTION, END_OF_INPUT ) },

			{ "let AAA = 1, let bbb = 1, eval AAA -> bbb <-> bbb' -> AAA'?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "AAA"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "bbb"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, EVAL_KEYWORD, withValue(IDENTIFIER, "AAA"), ARROW, withValue(IDENTIFIER, "bbb"), DOUBLE_ARROW, withValue(IDENTIFIER, "bbb"), APOSTROPHE, ARROW, withValue(IDENTIFIER, "AAA"), APOSTROPHE, QUESTION, END_OF_INPUT ) },
			{ "let aaa = 1, let BBB = 0, eval aaa -> BBB <-> BBB' -> aaa'?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "aaa"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "BBB"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, EVAL_KEYWORD, withValue(IDENTIFIER, "aaa"), ARROW, withValue(IDENTIFIER, "BBB"), DOUBLE_ARROW, withValue(IDENTIFIER, "BBB"), APOSTROPHE, ARROW, withValue(IDENTIFIER, "aaa"), APOSTROPHE, QUESTION, END_OF_INPUT ) },
			{ "let Aaa = 0, let BBB = 1, eval Aaa -> BBB <-> BBB' -> Aaa'?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "Aaa"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "BBB"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, EVAL_KEYWORD, withValue(IDENTIFIER, "Aaa"), ARROW, withValue(IDENTIFIER, "BBB"), DOUBLE_ARROW, withValue(IDENTIFIER, "BBB"), APOSTROPHE, ARROW, withValue(IDENTIFIER, "Aaa"), APOSTROPHE, QUESTION, END_OF_INPUT ) },
			{ "let AAA = 0, let bbB = 0, eval AAA -> bbB <-> bbB' -> AAA'?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "AAA"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "bbB"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, EVAL_KEYWORD, withValue(IDENTIFIER, "AAA"), ARROW, withValue(IDENTIFIER, "bbB"), DOUBLE_ARROW, withValue(IDENTIFIER, "bbB"), APOSTROPHE, ARROW, withValue(IDENTIFIER, "AAA"), APOSTROPHE, QUESTION, END_OF_INPUT ) },

			{ "Let P = 1, Let Q = P, Eval P v Q?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "P"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "Q"), EQUAL, withValue(IDENTIFIER, "P"), COMMA, EVAL_KEYWORD, withValue(IDENTIFIER, "P"), VEE, withValue(IDENTIFIER, "Q"), QUESTION, END_OF_INPUT ) },
			{ "LET p = 0, LET Q = p, EVAL p V Q?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "p"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "Q"), EQUAL, withValue(IDENTIFIER, "p"), COMMA, EVAL_KEYWORD, withValue(IDENTIFIER, "p"), VEE, withValue(IDENTIFIER, "Q"), QUESTION, END_OF_INPUT ) },
			{ "LeT P = 1, lEt q = P', EvAl P v q?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "P"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "q"), EQUAL, withValue(IDENTIFIER, "P"), APOSTROPHE, COMMA, EVAL_KEYWORD, withValue(IDENTIFIER, "P"), VEE, withValue(IDENTIFIER, "q"), QUESTION, END_OF_INPUT ) },
			{ "leT p = 0, LEt q = p', eVaL p V q?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "p"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "q"), EQUAL, withValue(IDENTIFIER, "p"), APOSTROPHE, COMMA, EVAL_KEYWORD, withValue(IDENTIFIER, "p"), VEE, withValue(IDENTIFIER, "q"), QUESTION, END_OF_INPUT ) },

			{ "let LP = 1, let EQ = 1, let VR = LP' ^ EQ', eval VR?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "LP"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "EQ"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "VR"), EQUAL, withValue(IDENTIFIER, "LP"), APOSTROPHE, CARET, withValue(IDENTIFIER, "EQ"), APOSTROPHE, COMMA, EVAL_KEYWORD, withValue(IDENTIFIER, "VR"), QUESTION, END_OF_INPUT ) },
			{ "let LP = 1, let EQ = 0, let VR = LP' ^ EQ', eval VR?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "LP"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "EQ"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "VR"), EQUAL, withValue(IDENTIFIER, "LP"), APOSTROPHE, CARET, withValue(IDENTIFIER, "EQ"), APOSTROPHE, COMMA, EVAL_KEYWORD, withValue(IDENTIFIER, "VR"), QUESTION, END_OF_INPUT ) },
			{ "let LP = 0, let EQ = 1, let VR = LP' ^ EQ', eval VR?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "LP"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "EQ"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "VR"), EQUAL, withValue(IDENTIFIER, "LP"), APOSTROPHE, CARET, withValue(IDENTIFIER, "EQ"), APOSTROPHE, COMMA, EVAL_KEYWORD, withValue(IDENTIFIER, "VR"), QUESTION, END_OF_INPUT ) },
			{ "let LP = 0, let EQ = 0, let VR = LP' ^ EQ', eval VR?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "LP"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "EQ"), EQUAL, withValue(BOOL_LITERAL, false), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "VR"), EQUAL, withValue(IDENTIFIER, "LP"), APOSTROPHE, CARET, withValue(IDENTIFIER, "EQ"), APOSTROPHE, COMMA, EVAL_KEYWORD, withValue(IDENTIFIER, "VR"), QUESTION, END_OF_INPUT ) },

			{ "eval LET?", tokens(EVAL_KEYWORD, LET_KEYWORD, QUESTION, END_OF_INPUT ) },
			{ "let P = 1, eval p?", tokens(LET_KEYWORD, withValue(IDENTIFIER, "P"), EQUAL, withValue(BOOL_LITERAL, true), COMMA, EVAL_KEYWORD, withValue(IDENTIFIER, "p"), QUESTION, END_OF_INPUT ) },
			{ "eval Q, let P = 0?", tokens(EVAL_KEYWORD, withValue(IDENTIFIER, "Q"), COMMA, LET_KEYWORD, withValue(IDENTIFIER, "P"), EQUAL, withValue(BOOL_LITERAL, false), QUESTION, END_OF_INPUT ) },
			{ "let -><-><->->EP? LQ", tokens(LET_KEYWORD, ARROW, DOUBLE_ARROW, DOUBLE_ARROW, ARROW, withValue(IDENTIFIER, "EP"), QUESTION, withValue(IDENTIFIER, "LQ"), END_OF_INPUT ) },
			{ "Vvv <->-><--> 1", tokens(withValue(IDENTIFIER, "Vvv"), DOUBLE_ARROW, ARROW, UNEXPECTED_INPUT ) },
			{ "^vEVAL letEVAL ' V Pv & ^", tokens(CARET, withValue(IDENTIFIER, "vEVAL"), withValue(IDENTIFIER, "letEVAL"), APOSTROPHE, VEE, withValue(IDENTIFIER, "Pv"), UNEXPECTED_INPUT ) },
		};
	}

	public static Queue<Token> tokens(Object... tokens) {
		Queue<Token> expected = new LinkedList<Token>();
		for (Object it: tokens) {
			if (it instanceof TokenName)
				expected.add(new Token((TokenName) it));
			else
				expected.add((Token) it);
		}
		return expected;
	}

	public static Token withValue(TokenName name, Object value) {
		return new Token(name, value);
	}

	@Parameter(value=0)
	public String sentence;

	@Parameter(value=1)
	public Queue<Token> expected;

	@Test
	public void testTokens() {
		Queue<Token> actual = new Lexer().tokenize(sentence);
		assertNotNull(
			"Must return a non-null queue of tokens,",
			actual
		);

		int elen = expected.size();
		int alen = actual.size();
		int len = Math.min(elen, alen);

		for (int i = 0; i < len; i++) {
			Token e = expected.poll();
			Token a = actual.poll();

			assertEquals(
				"Token " + (i+1) + " of " + len + " must have expected name,",
				e.name,
				a.name
			);

			assertEquals(
				"Token " + (i+1) + " of " + len + " must have expected value,",
				e.value,
				a.value
			);
		}

		assertEquals(
			"Must return expected number of tokens,",
			elen,
			alen
		);
	}
}
