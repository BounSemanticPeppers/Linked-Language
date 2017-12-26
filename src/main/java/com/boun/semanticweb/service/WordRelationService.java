package com.boun.semanticweb.service;

import com.boun.semanticweb.model.Word;
import com.boun.semanticweb.model.WordRelation;
import com.boun.semanticweb.viewModel.GraphData;

public interface WordRelationService {

    GraphData getWordRelations(Word word);

}
