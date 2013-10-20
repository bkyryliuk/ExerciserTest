package com.gma.exerciser.test.DAO;

import java.util.ArrayList;

import com.gma.exerciser.DAO.DictionaryDAO;
import com.gma.exerciser.Pojo.Dictionary;
import com.gma.exerciser.Pojo.Word;

public class MockDictionaryDAO extends DictionaryDAO{

	@Override
	public ArrayList<Word> getCurrentWordChunk(int size) {
		ArrayList<Word> words = new ArrayList<Word>();
		for (int i = 0; i < size; i++) {
			Word word = new Word();
			word.setWord("word" + i);
			word.setMain_translation("translation" + i);
			words.add(word);
		}
		return words;
	}
	@Override
	public ArrayList<Word> getAllWords(Dictionary dict) {
		return null;
	}
	@Override
	public ArrayList<Word> getLearnedWords(Dictionary dict) {
		return null;
	}
	@Override
	public ArrayList<Dictionary> getAllDictionaries() {
		return null;
	}

	// TODO keep data in shared preferences to get it
	@Override
	public Dictionary getCurrentDictionary() {
		return null;
	}
}
