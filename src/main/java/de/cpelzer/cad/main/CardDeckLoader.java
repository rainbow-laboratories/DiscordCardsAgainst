package de.cpelzer.cad.main;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Class which loads the card deck XML.
 * 
 * @author srolfes
 *
 */
public class CardDeckLoader {
	/**
	 * The black cards. These are the cards with the questions.
	 */
	private BlackCard[] blackCards;
	/**
	 * The white cards. These are the cards with the answers.
	 */
	private String[] whiteCards;

	/**
	 * Constructor which triggers the loading of the card deck.
	 * 
	 * @param filename
	 *            The filename of the XML file, where the card deck is loaded from.
	 */
	public CardDeckLoader(String filename) {
		loadCardDeck(filename);

		System.out.println(Arrays.deepToString(blackCards));// Remove
		System.out.println(Arrays.deepToString(whiteCards));// Remove
	}

	/**
	 * Method which loads the file and triggers the loading of the card deck.
	 * 
	 * @param filename
	 *            The filename of the XML file, where the card deck is loaded from.
	 */
	private void loadCardDeck(String filename) {
		try {
			File file = new File(filename);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			try {
				Document document = dBuilder.parse(file);
				document.getDocumentElement().normalize();

				loadBlackCards(document);
				loadWhiteCards(document);
			} catch (IOException | SAXException e) {
				e.printStackTrace();
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Loads the black cards from the document.
	 * 
	 * @param document
	 *            The document where the cards should be loaded from.
	 */
	private void loadBlackCards(Document document) {
		NodeList nl = document.getElementsByTagName("blackCards");

		if (nl.getLength() > 0) {
			NodeList cardList = nl.item(0).getChildNodes();

			int cardsLength = getRealNodeListLength(cardList);
			int cardPos = 0;

			blackCards = new BlackCard[cardsLength];
			for (int i = 0; i < cardList.getLength(); i++) {
				Node card = cardList.item(i);

				if (card instanceof Element) {
					Element cardElem = (Element) card;
					NodeList partList = cardElem.getChildNodes();

					String answerPositions = cardElem.getAttribute("answerPositions");
					String[] apSplitted = answerPositions.split(" ");

					BlackCard bc = new BlackCard(apSplitted);

					int partsLength = getRealNodeListLength(partList);
					int partPos = 0;

					String[] parts = new String[partsLength];
					for (int j = 0; j < partList.getLength(); j++) {
						Node part = partList.item(j);

						if (part instanceof Element) {
							Element partElem = (Element) part;
							parts[partPos] = partElem.getTextContent();

							partPos++;
						}
					}

					bc.setParts(parts);
					blackCards[cardPos] = bc;

					cardPos++;
				}
			}
		}
	}

	/**
	 * Loads the white cards from the document.
	 * 
	 * @param document
	 *            The document where the cards should be loaded from.
	 */
	private void loadWhiteCards(Document document) {
		NodeList nl = document.getElementsByTagName("whiteCards");

		if (nl.getLength() > 0) {
			NodeList cardList = nl.item(0).getChildNodes();

			int cardsLength = getRealNodeListLength(cardList);
			int cardPos = 0;

			whiteCards = new String[cardsLength];
			for (int i = 0; i < cardList.getLength(); i++) {
				Node card = cardList.item(i);

				if (card instanceof Element) {
					Element cardElem = (Element) card;
					whiteCards[cardPos] = cardElem.getTextContent();

					cardPos++;
				}
			}
		}
	}

	/**
	 * Calculates the real length of the NodeList.
	 * 
	 * @param nl
	 *            The NodeList where the length needs to be calculated from.
	 * @return The real length of the NodeList.
	 */
	private int getRealNodeListLength(NodeList nl) {
		int length = 0;
		for (int i = 0; i < nl.getLength(); i++) {
			if (nl.item(i) instanceof Element) {
				length++;
			}
		}
		return length;
	}

	// Remove
	public static void main(String[] args) {
		new CardDeckLoader("res/CardsAgainstHumanity.xml");
	}
	
	// Outsource into core game engine
		/*
		 * public String[] getBlackCards() {
		 * 
		 * return null; }
		 * 
		 * public String[] getWhiteCards() {
		 * 
		 * return null; }
		 * 
		 * public String getRandomBlackCard() {
		 * 
		 * return null; }
		 * 
		 * public String getRandomWhiteCard() {
		 * 
		 * return null; }
		 */
}
