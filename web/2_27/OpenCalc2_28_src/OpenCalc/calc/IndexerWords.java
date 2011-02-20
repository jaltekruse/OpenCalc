package calc;

/*
 OpenCalc is a Graphing Calculator for the web.
 Copyright (C) 2009, 2010 Jason Altekruse

 This file is part of OpenCalc.

 OpenCalc is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 OpenCalc is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with OpenCalc  If not, see <http://www.gnu.org/licenses/>.
 */

import java.util.ArrayList;

public class IndexerWords {

	private ArrayList[] wordList = new ArrayList[676];
	private ArrayList[] numList = new ArrayList[81];
	private ArrayList[] underscoreList = new ArrayList[12];
	private final static int MAX_INDEXERS = 10;

	private static String[] parseVarName(String s) {
		String[] indexerWords = new String[MAX_INDEXERS];
		char currChar;
		int numIndexerWords = 0;
		int currCharNum = 0;
		boolean hitEnd = false;

		for (int i = 0; i < indexerWords.length; i++) {
			indexerWords[i] = "";
		}

		do {
			currChar = s.charAt(currCharNum);
			if (currChar == '_') {
				for (; currChar == '_'; currCharNum++, currChar = s
						.charAt(currCharNum)) {
					indexerWords[numIndexerWords] += currChar;
					if (currCharNum >= s.length() - 1) {
						hitEnd = true;
						currChar = '\0';
						break;
					}
				}
				numIndexerWords++;
			}

			else if (currChar >= 'a' && currChar <= 'z') {
				for (; currChar >= 'a' && currChar <= 'z'; currCharNum++, currChar = s
						.charAt(currCharNum)) {
					indexerWords[numIndexerWords] += currChar;
					if (currCharNum >= s.length() - 1) {
						hitEnd = true;
						currChar = '\0';
						break;
					}
				}
				numIndexerWords++;
			} else if (currChar >= 'A' && currChar <= 'Z') {
				char tempChar = s.charAt(currCharNum + 1);
				if (tempChar >= 'A' && tempChar <= 'Z') {
					for (; currChar >= 'A' && currChar <= 'Z'; currCharNum++, currChar = s
							.charAt(currCharNum)) {
						indexerWords[numIndexerWords] += Character
								.toLowerCase(currChar);
						if (currCharNum >= s.length() - 1) {
							hitEnd = true;
							currChar = '\0';
							break;
						}
					}
				} else if (tempChar >= 'a' && tempChar <= 'z') {
					boolean lastIsUpper = true;
					int numCapChanges = 0;

					for (; Character.isLetter(currChar); currCharNum++, currChar = s
							.charAt(currCharNum)) {
						if (Character.isLowerCase(currChar)) {
							if (lastIsUpper) {
								numCapChanges++;
								lastIsUpper = false;
							}
						} else if (Character.isUpperCase(currChar)) {
							if (!lastIsUpper) {
								numCapChanges++;
								lastIsUpper = true;
							}
						}
						if (numCapChanges > 1)
							break;
						indexerWords[numIndexerWords] += Character
								.toLowerCase(currChar);
						if (currCharNum >= s.length() - 1) {
							hitEnd = true;
							currChar = '\0';
							break;
						}
					}
				}
				numIndexerWords++;
			} else if (Character.isDigit(currChar)) {
				for (; Character.isDigit(currChar); currCharNum++, currChar = s
						.charAt(currCharNum)) {
					indexerWords[numIndexerWords] += currChar;
					if (currCharNum >= s.length() - 1) {
						hitEnd = true;
						currChar = '\0';
						break;
					}
				}
				numIndexerWords++;
			}
			if (hitEnd)
				break;
			else
				continue;
		} while (currCharNum <= s.length());

		return indexerWords;
	}

	public static void main(String[] args) {
		String s = "speed6OFlight_123";
		String[] indexerWords = parseVarName(s);
		for (int i = 0; i < indexerWords.length; i++) {
			System.out.println(indexerWords[i]);
		}
	}
}
