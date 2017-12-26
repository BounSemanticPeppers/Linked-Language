package com.boun.semanticweb.web;

import com.boun.semanticweb.base.JsonHandler;
import com.boun.semanticweb.model.Word;
import com.boun.semanticweb.service.WordRelationService;
import com.boun.semanticweb.service.WordService;
import com.boun.semanticweb.viewModel.GameRecord;
import com.boun.semanticweb.viewModel.GraphData;
import com.boun.semanticweb.wikidata.SearchEntities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.wikidata.wdtk.datamodel.interfaces.EntityDocument;
import org.wikidata.wdtk.datamodel.interfaces.ItemDocument;
import org.wikidata.wdtk.datamodel.interfaces.Statement;
import org.wikidata.wdtk.datamodel.interfaces.StatementDocument;
import org.wikidata.wdtk.wikibaseapi.WbSearchEntitiesResult;

import javax.servlet.http.HttpServletResponse;

@Controller
public class SearchController {

    @Autowired
    private WordRelationService wordRelationService;

    @Autowired
    private WordService wordService;

    @RequestMapping(value = "/searchResult", method = RequestMethod.GET)
    public String searchResult(HttpServletResponse response) {
        return "searchResult";
    }

    @RequestMapping(value = "/getRelatedWords", method = RequestMethod.POST)
    public void getRelatedWords(@ModelAttribute("searchWord") String searchWord, HttpServletResponse response) throws Exception {

        Word word = wordService.findByText(searchWord);

        if (word != null){
            GraphData graphData =  wordRelationService.getWordRelations(word);
            response.getWriter().write(JsonHandler.convertToJSON(graphData));
        }else{
            response.getWriter().write(JsonHandler.convertToJSON("Word not found"));
        }

    }

    @RequestMapping(value = "/searchWord", method = RequestMethod.POST)
    public void searchWord(@ModelAttribute("searchWord") String searchWord, HttpServletResponse response) throws Exception {

        Word word = wordService.findByText(searchWord);

        if (word != null){
            response.getWriter().write(JsonHandler.convertToJSON(word));
        }else{
            response.getWriter().write(JsonHandler.convertToJSON("Word not found"));
        }

    }

    @RequestMapping(value = "/getWikiDataOfWord", produces = "application/json;charset=UTF-8", method = RequestMethod.POST)
    public void searchWord(@ModelAttribute("wordId") Long wordId, HttpServletResponse response) throws Exception {

        Word word = wordService.findByWordId(wordId);

        SearchEntities searcher = new SearchEntities();
        WbSearchEntitiesResult result = searcher.search(word.getText());
        ItemDocument result2 = searcher.getEntity(result.getEntityId());

        //Statement st = result2.findStatement("P18");

        //System.out.print("statemeent value is "+ st.getClaim().getMainSnak().getValue().toString());
        if (result != null){
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(JsonHandler.convertToJSON(result2));
        }else{
            response.getWriter().write(JsonHandler.convertToJSON("result not found"));
        }
    }
}
