/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boun.semanticweb.service;

import com.boun.semanticweb.base.CommonUserOperations;
import com.boun.semanticweb.model.Word;
import com.boun.semanticweb.repository.WordRepository;

import java.util.Date;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author onurm
 */
@Service
public class WordServiceImpl implements WordService{
    @Autowired
    private WordRepository wordRepository;

    @Override
    public void save(Word word) {
        wordRepository.save(word);
    }

    @Override
    public Word findByWordId(Long wordId) {
        return wordRepository.findById(wordId);
    }

    @Override
    public Word findByText(String text) {
        return wordRepository.findByText(text);
    }
    
    @Override
    public Word getRandomWord(){
        Long maxID = wordRepository.getMaxWordId();
        Random rand = new Random();
        Integer nextRand = 0;
        Word randWord = null;

        while(randWord == null){
            nextRand = rand.nextInt(maxID.intValue()) + 1;
            randWord = wordRepository.findById(nextRand.longValue());
        }
        return randWord;
    }

    @Override
    public Word getOrSaveFeedWord(String feedWord){
        Word word = wordRepository.findByText(feedWord);

        if(word == null || word.getId() == null){
            Word newWord = new Word();
            newWord.setText(feedWord);
            newWord.setCreatedDate(new Date());
            newWord.setCreatedBy(CommonUserOperations.getUserId());

            wordRepository.save(newWord);
            return newWord;
        }else{
            return word;
        }
    }

}
