/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boun.semanticweb.service;

import com.boun.semanticweb.model.Word;

/**
 *
 * @author onurm
 */
public interface WordService {
    void save(Word word);

    Word findByWordId(Long wordId);
    
    Word findByText(String text);
    
    Word getRandomWord();

    Word getOrSaveFeedWord(String word);
}
