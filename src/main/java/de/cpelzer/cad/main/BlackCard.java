package de.cpelzer.cad.main;

import java.util.Arrays;

/**
 * This class represents a black card. These are the question cards. It saves
 * the separate parts of the question and the positions where the answers need
 * to be pasted to.
 * 
 * @author srolfes
 *
 */
public class BlackCard {
	/**
	 * Placeholder for answers.
	 */
	private static String PLACEHOLDER = "____";
	/**
	 * The separate parts of the question.
	 */
	private String[] parts;
	/**
	 * The positions where the answers need to be pasted.
	 */
	private int[] answerPositions;

	/**
	 * Constructor which converts and saves the positions of the answers.
	 * 
	 * @param answerPositions
	 *            The positions where the answers need to be pasted to.
	 */
	public BlackCard(String[] answerPositions) {
		this.answerPositions = new int[answerPositions.length];
		for (int i = 0; i < answerPositions.length; i++) {
			this.answerPositions[i] = Integer.valueOf(answerPositions[i]);
		}

		Arrays.sort(this.answerPositions);
		if (this.answerPositions.length > 0 && this.answerPositions[0] < 0) {
			throw new RuntimeException("Invalid position of answer. Can't start with values lower 0.");
		}
	}

	/**
	 * Setter for the separate parts of the question.
	 * 
	 * @param parts
	 *            Separate parts of the question.
	 */
	public void setParts(String[] parts) {
		this.parts = parts;
	}

	/**
	 * Getter for the separate parts of the question.
	 * 
	 * @return Separate parts of the question.
	 */
	public String[] getParts() {
		return parts;
	}

	/**
	 * Getter for the positions where the answers need to be pasted to.
	 * 
	 * @return Positions where the answers need to be pasted to.
	 */
	public int[] getAnswerPositions() {
		return answerPositions;
	}

	@Override
	public String toString() {
		String s = "";

		int apPos = 0;
		for (int pPos = 0; pPos <= parts.length; pPos++) {
			s += (pPos == 0) ? "" : " ";

			for (int i = apPos; i < answerPositions.length; i++) {
				if (answerPositions[i] == pPos) {
					s += PLACEHOLDER + ((pPos < parts.length) ? " " : "");
					apPos++;
				} else if (answerPositions[i] > pPos) {
					break;
				}
			}

			if (pPos < parts.length) {
				s += parts[pPos];
			}
		}

		return s;
	}

	/**
	 * ToString method which parses the answers into the correct positions.
	 * 
	 * @param answers
	 *            The answers which need to be parsed into the string.
	 * @return The question with parsed answers.
	 */
	public String toString(String[] answers) {
		String s = "";

		int apPos = 0;
		for (int pPos = 0; pPos <= parts.length; pPos++) {
			s += (pPos == 0) ? "" : " ";

			for (int i = apPos; i < answerPositions.length; i++) {
				if (answerPositions[i] == pPos) {
					s += answers[apPos] + ((pPos < parts.length) ? " " : "");
					apPos++;
				} else if (answerPositions[i] > pPos) {
					break;
				}
			}

			if (pPos < parts.length) {
				s += parts[pPos];
			}
		}

		return s;
	}
}
