package edu.cmu.meteor.aligner;

/**
 * Universal interface for all kinds of stemmers
 */
public interface Stemmer {
	public String stem(String word);
	
	public boolean hasSingle();
	
	public String[] stemMulti(String word);
}