/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.boun.semanticweb.repository;

import com.boun.semanticweb.model.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author onurm
 */
public interface WordRepository extends JpaRepository<Word, Long> {    
    Word findById(Long id);
    
    Word findByText(String text);
    
    @Query("select max(id) from Word")
    Long getMaxWordId();
}
