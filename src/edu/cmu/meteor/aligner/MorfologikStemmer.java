package edu.cmu.meteor.aligner;

import java.util.List;

import morfologik.stemming.PolishStemmer;
import morfologik.stemming.WordData;

public class MorfologikStemmer implements Stemmer {

	@Override
	public String stem(String word) {
		return null;
	}

	@Override
	public boolean hasSingle() {
		return false;
	}

	private PolishStemmer stemmer = new PolishStemmer();

	@Override
	public String[] stemMulti(String word) {

		List<WordData> words = stemmer.lookup(word);

		String[] ret = new String[words.size()];

		for (int i = 0; i < ret.length; i++) {
			ret[i] = words.get(i).getStem().toString();
		}

		return ret;
	}

	// Unit test
	// public static void main(String[] args) {
	// MorfologikStemmer stemmer = new MorfologikStemmer();
	// String[] ret = stemmer.stemMulti("ma");
	// for (String w : ret) {
	// System.out.println(w);
	// }
	// }
}
