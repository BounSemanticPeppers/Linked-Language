package com.boun.semanticweb.service;

import com.boun.semanticweb.model.Word;
import com.boun.semanticweb.model.WordRelation;
import com.boun.semanticweb.repository.WordRelationRepository;
import com.boun.semanticweb.repository.WordRepository;
import com.boun.semanticweb.viewModel.Edge;
import com.boun.semanticweb.viewModel.GraphData;
import com.boun.semanticweb.viewModel.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class WordRelationServiceImpl implements WordRelationService {

    @Autowired
    private WordRelationRepository wordRelationRepository;

    @Autowired
    private WordRepository wordRepository;

    @Override
    public GraphData getWordRelations(Word word) {

        List<WordRelation> wordRelationList = wordRelationRepository.findBySourceWordIdOrTargetWordId(word.getId(),word.getId());

        List<Node> nodeList = new ArrayList<>();
        List<Edge> edgeList = new ArrayList<>();
        List<Long> wordIds = new ArrayList<>();

        for (WordRelation wordRelation : wordRelationList){
            wordIds.add(wordRelation.getSourceWordId());
            wordIds.add(wordRelation.getTargetWordId());
            edgeList.add(new Edge(wordRelation.getSourceWordId().intValue(), wordRelation.getTargetWordId().intValue(), wordRelation.getScore()));
        }
        Set<Long> uniqueWordIds = new HashSet<>(wordIds);

        for(Long id : uniqueWordIds){
            Word current = wordRepository.findById(id);
            nodeList.add(new Node(current.getId().intValue(), current.getText()));
        }

        return new GraphData(nodeList,edgeList);

    }
}
