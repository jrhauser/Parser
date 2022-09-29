package grade;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;
import org.junit.function.ThrowingRunnable;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import unit.Parser;

/**
 * Do not modify this class.
 */
@RunWith(Parameterized.class)
public class ParserTests {
	@Parameters(name="{index}: {0}")
	public static Object[][] data() {
		return new Object[][] {
			{ "eval 1?", true },
			{ "eval 0?", false },
			{ "eval 1'?", false },
			{ "eval 0'?", true },

			{ "eval 1 -> 1?", true },
			{ "eval 1 -> 0?", false },
			{ "eval 0 -> 1?", true },
			{ "eval 0 -> 0?", true },

			{ "eval 1 <-> 1?", true },
			{ "eval 1 <-> 0?", false },
			{ "eval 0 <-> 1?", false },
			{ "eval 0 <-> 0?", true },

			{ "let P = 1, let Q = 1, eval P ^ Q?", true },
			{ "let P = 1, let Q = 0, eval P ^ Q?", false },
			{ "let P = 0, let Q = 1, eval P ^ Q?", false },
			{ "let P = 0, let Q = 0, eval P ^ Q?", false },

			{ "let P = 1, let Q = 1, let R = 1, eval P ^ Q ^ R?", true },
			{ "let P = 1, let Q = 1, let R = 0, eval P ^ Q ^ R?", false },
			{ "let P = 1, let Q = 0, let R = 1, eval P ^ Q ^ R?", false },
			{ "let P = 1, let Q = 0, let R = 0, eval P ^ Q ^ R?", false },
			{ "let P = 0, let Q = 1, let R = 1, eval P ^ Q ^ R?", false },
			{ "let P = 0, let Q = 1, let R = 0, eval P ^ Q ^ R?", false },
			{ "let P = 0, let Q = 0, let R = 1, eval P ^ Q ^ R?", false },
			{ "let P = 0, let Q = 0, let R = 0, eval P ^ Q ^ R?", false },

			{ "let A = 1, let B = 1, eval A v B?", true },
			{ "let A = 1, let B = 0, eval A v B?", true },
			{ "let A = 0, let B = 1, eval A v B?", true },
			{ "let A = 0, let B = 0, eval A v B?", false },

			{ "let A = 1, let B = 1, let C = 1, eval A v B v C?", true },
			{ "let A = 1, let B = 1, let C = 0, eval A v B v C?", true },
			{ "let A = 1, let B = 0, let C = 1, eval A v B v C?", true },
			{ "let A = 1, let B = 0, let C = 0, eval A v B v C?", true },
			{ "let A = 0, let B = 1, let C = 1, eval A v B v C?", true },
			{ "let A = 0, let B = 1, let C = 0, eval A v B v C?", true },
			{ "let A = 0, let B = 0, let C = 1, eval A v B v C?", true },
			{ "let A = 0, let B = 0, let C = 0, eval A v B v C?", false },

			{ "let X = 1, let Y = 1, eval X -> Y?", true },
			{ "let X = 1, let Y = 0, eval X -> Y?", false },
			{ "let X = 0, let Y = 1, eval X -> Y?", true },
			{ "let X = 0, let Y = 0, eval X -> Y?", true },

			{ "let X = 1, let Y = 1, let Z = 1, eval X -> Y -> Z?", true },
			{ "let X = 1, let Y = 1, let Z = 0, eval X -> Y -> Z?", false },
			{ "let X = 1, let Y = 0, let Z = 1, eval X -> Y -> Z?", true },
			{ "let X = 1, let Y = 0, let Z = 0, eval X -> Y -> Z?", true },
			{ "let X = 0, let Y = 1, let Z = 1, eval X -> Y -> Z?", true },
			{ "let X = 0, let Y = 1, let Z = 0, eval X -> Y -> Z?", true },
			{ "let X = 0, let Y = 0, let Z = 1, eval X -> Y -> Z?", true },
			{ "let X = 0, let Y = 0, let Z = 0, eval X -> Y -> Z?", true },

			{ "let D = 1, let E = 1, eval D <-> E?", true },
			{ "let D = 1, let E = 0, eval D <-> E?", false },
			{ "let D = 0, let E = 1, eval D <-> E?", false },
			{ "let D = 0, let E = 0, eval D <-> E?", true },

			{ "let D = 1, let E = 1, let F = 1, eval D <-> E <-> F?", true },
			{ "let D = 1, let E = 1, let F = 0, eval D <-> E <-> F?", false },
			{ "let D = 1, let E = 0, let F = 1, eval D <-> E <-> F?", false },
			{ "let D = 1, let E = 0, let F = 0, eval D <-> E <-> F?", true },
			{ "let D = 0, let E = 1, let F = 1, eval D <-> E <-> F?", false },
			{ "let D = 0, let E = 1, let F = 0, eval D <-> E <-> F?", true },
			{ "let D = 0, let E = 0, let F = 1, eval D <-> E <-> F?", true },
			{ "let D = 0, let E = 0, let F = 0, eval D <-> E <-> F?", false },

			{ "let W = 1, eval W?", true },
			{ "let W = 0, eval W?", false },
			{ "let W = 1, eval W'?", false },
			{ "let W = 0, eval (W')'?", false },
			{ "let W = 1, eval (W)?", true },
			{ "let W = 0, eval ((W))?", false },

			{ "let AB = 1, let Q = 1, eval (AB ^ Q)' <-> (AB' v Q')?", true },
			{ "let AB = 1, let Q = 0, eval (AB ^ Q)' <-> (AB' v Q')?", true },
			{ "let AB = 0, let Q = 1, eval (AB ^ Q)' <-> (AB' v Q')?", true },
			{ "let AB = 0, let Q = 0, eval (AB ^ Q)' <-> (AB' v Q')?", true },

			{ "let P = 1, let CD = 1, eval (P ^ CD)' <-> (P' v CD')?", true },
			{ "let P = 1, let CD = 0, eval (P ^ CD)' <-> (P' v CD')?", true },
			{ "let P = 0, let CD = 1, eval (P ^ CD)' <-> (P' v CD')?", true },
			{ "let P = 0, let CD = 0, eval (P ^ CD)' <-> (P' v CD')?", true },

			{ "let JKL = 1, let Q = 1, eval (JKL -> Q) <-> (JKL' v Q)?", true },
			{ "let JKL = 1, let Q = 0, eval (JKL -> Q) <-> (JKL' v Q)?", true },
			{ "let JKL = 0, let Q = 1, eval (JKL -> Q) <-> (JKL' v Q)?", true },
			{ "let JKL = 0, let Q = 0, eval (JKL -> Q) <-> (JKL' v Q)?", true },

			{ "let P = 1, let MNO = 1, eval P -> MNO <-> P' v MNO?", true },
			{ "let P = 1, let MNO = 0, eval P -> MNO <-> P' v MNO?", true },
			{ "let P = 0, let MNO = 1, eval P -> MNO <-> P' v MNO?", true },
			{ "let P = 0, let MNO = 0, eval P -> MNO <-> P' v MNO?", true },

			{ "let AAA = 1, let bbb = 1, eval AAA -> bbb <-> bbb' -> AAA'?", true },
			{ "let aaa = 1, let BBB = 0, eval aaa -> BBB <-> BBB' -> aaa'?", true },
			{ "let Aaa = 0, let BBB = 1, eval Aaa -> BBB <-> BBB' -> Aaa'?", true },
			{ "let AAA = 0, let bbB = 0, eval AAA -> bbB <-> bbB' -> AAA'?", true },

			{ "Let P = 1, Let Q = P, Eval Q?", true },
			{ "LET P = 0, LET Q = P, EVAL Q?", false },
			{ "LeT P = 1, lEt Q = P', EvAl Q?", false },
			{ "leT P = 0, LEt Q = P', eVaL Q?", true },

			{ "let LP = 1, let EQ = 1, let VR = LP' ^ EQ', eval VR?", false },
			{ "let LP = 1, let EQ = 0, let VR = LP' ^ EQ', eval VR?", false },
			{ "let LP = 0, let EQ = 1, let VR = LP' ^ EQ', eval VR?", false },
			{ "let LP = 0, let EQ = 0, let VR = LP' ^ EQ', eval VR?", true },

			{ "let P = 0, Q = 0, R = 0, eval !P && !Q && !R?", RuntimeException.class},
			{ "let P = 1, eval Q?", RuntimeException.class },
			{ "let P = 1, eval p?", RuntimeException.class },
			{ "let P = 1, let P = 0, eval P?", RuntimeException.class },
			{ "let A = 1, let B = 1, let C = 1, let D = 0, let E = 0, let F = 1, let G = 0, let H = 0, eval A ^ B ^ C ^ F?", true },
			{ "let A = 1, let B = 1, let C = 1, let D = 0, let E = 0, let F = 1, let G = 0, let H = 0, let I = 1, eval A ^ B ^ C ^ F ^ I?", RuntimeException.class }
		};
	}

	@Parameter(value=0)
	public String sentence;

	@Parameter(value=1)
	public Object expected;

	@SuppressWarnings("unchecked")
	@Test
	public void testProgram() {
		if (expected instanceof Boolean) {
			assertEquals(
				"Valid sentence must return correct boolean analysis,",
				expected,
				new Parser().analyze(sentence)
			);
		}
		else {
			assertThrows(
				"Invalid sentence must throw exception,",
				(Class<? extends Throwable>) expected,
				new ThrowingRunnable() {
					public void run() throws Throwable {
						new Parser().analyze(sentence);
					}
				}
			);
		}
	}
}
