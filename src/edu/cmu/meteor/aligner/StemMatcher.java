/*
 * Carnegie Mellon University
 * Copyright (c) 2004, 2010
 * 
 * This software is distributed under the terms of the GNU Lesser General
 * Public License.  See the included COPYING and COPYING.LESSER files.
 * 
 */

package edu.cmu.meteor.aligner;

import java.util.ArrayList;

public class StemMatcher {

	public static void match(int stage, Alignment a, Stage s, Stemmer stemmer) {

		if (stemmer.hasSingle()) {
			// Get keys for word stems
			int[] stems1 = wordsToStemKeys(a.words1, stemmer);
			int[] stems2 = wordsToStemKeys(a.words2, stemmer);

			for (int j = 0; j < stems2.length; j++) {

				for (int i = 0; i < stems1.length; i++) {

					// Match for DIFFERENT words with SAME stems
					if (stems1[i] == stems2[j] && s.words1[i] != s.words2[j]) {

						Match m = new Match();
						m.module = stage;
						m.prob = 1;
						m.start = j;
						m.length = 1;
						m.matchStart = i;
						m.matchLength = 1;

						// Add this match to the list of matches and mark
						// coverage
						s.matches.get(j).add(m);
						s.line1Coverage[i]++;
						s.line2Coverage[j]++;
					}
				}
			}
		} else {
			int[][] stems1 = wordsToStemKeysSet(a.words1, stemmer);
			int[][] stems2 = wordsToStemKeysSet(a.words2, stemmer);

			for (int j = 0; j < stems2.length; j++) {

				for (int i = 0; i < stems1.length; i++) {

					// Match for DIFFERENT words with SAME stems (less costly
					// comparison first)
					if (s.words1[i] != s.words2[j]
							&& matchesAny(stems1[i], stems2[j])) {

						Match m = new Match();
						m.module = stage;
						m.prob = 1;
						m.start = j;
						m.length = 1;
						m.matchStart = i;
						m.matchLength = 1;

						// Add this match to the list of matches and mark
						// coverage
						s.matches.get(j).add(m);
						s.line1Coverage[i]++;
						s.line2Coverage[j]++;
					}
				}
			}
		}
	}

	//check if any of the values in the two sets match	
	private static boolean matchesAny(int[] a, int[] b) {
		for (int i = 0; i < a.length; i++)
			for (int j = 0; j < b.length; j++) {
				if (a[i] == b[j])
					return true;
			}

		return false;
	}

	private static int[] wordsToStemKeys(ArrayList<String> words,
			Stemmer stemmer) {
		int[] keys = new int[words.size()];
		for (int i = 0; i < words.size(); i++) {
			// Stem the word before generating a key
			keys[i] = stemmer.stem(words.get(i)).hashCode();
		}
		return keys;
	}

	private static int[][] wordsToStemKeysSet(ArrayList<String> words,
			Stemmer stemmer) {
		int[][] keys = new int[words.size()][];
		for (int i = 0; i < words.size(); i++) {
			// Stem the word before generating a key
			String[] stems = stemmer.stemMulti(words.get(i));
			keys[i] = new int[stems.length];
			for (int j = 0; j < stems.length; j++) {
				keys[i][j] = stems[j].hashCode();
			}
		}
		return keys;
	}
}
