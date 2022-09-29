package framework;

/**
 * Do not modify this class.
 */
public enum TokenName {
	LET_KEYWORD,  // regex /let/i	  matches let (case insensitive)
	EVAL_KEYWORD, // regex /eval/i	  matches eval (case insensitive)
	VEE,		  // regex /v/i		  matches v (case insensitive)
	IDENTIFIER,	  // regex /[a-z]+/i  matches 1+ letters (case insensitive)

	// matches all of the above (case insensitive):
	// regex /(let|eval|v)(?![a-z])|([a-z]+)/i
	// uses (?!pattern) for negative lookahead

	EQUAL,		  // regex /=/		matches =
	COMMA,		  // regex /,/		matches ,
	QUESTION,	  // regex /\?/		matches ?
	DOUBLE_ARROW, // regex /<->/	matches <->
	ARROW,		  // regex /->/		matches ->
	CARET,		  // regex /\^/		matches ^
	APOSTROPHE,	  // regex /'/		matches '
	OPEN_PAREN,	  // regex /\(/		matches (
	CLOSE_PAREN,  // regex /\)/		matches )
	BOOL_LITERAL, // regex /[01]/	matches 0 or 1

	END_OF_INPUT,
	UNEXPECTED_INPUT
}