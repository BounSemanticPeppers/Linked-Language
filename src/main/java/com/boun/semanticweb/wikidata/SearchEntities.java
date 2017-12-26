package com.boun.semanticweb.wikidata;

import org.wikidata.wdtk.datamodel.helpers.Datamodel;
import org.wikidata.wdtk.datamodel.interfaces.EntityDocument;
import org.wikidata.wdtk.datamodel.interfaces.ItemDocument;
import org.wikidata.wdtk.datamodel.interfaces.Statement;
import org.wikidata.wdtk.datamodel.interfaces.StatementDocument;
import org.wikidata.wdtk.wikibaseapi.ApiConnection;
import org.wikidata.wdtk.wikibaseapi.WbSearchEntitiesAction;
import org.wikidata.wdtk.wikibaseapi.WbSearchEntitiesResult;
import org.wikidata.wdtk.wikibaseapi.WikibaseDataFetcher;
import org.wikidata.wdtk.wikibaseapi.apierrors.MediaWikiApiErrorException;

import java.util.List;
import java.util.Map;

public class SearchEntities {

    String BASE_URL = "https://www.wikidata.org/w/api.php/";

    ApiConnection con;
    WbSearchEntitiesAction action;

    public SearchEntities(){
        this.con = new ApiConnection(BASE_URL);
        this.action = new WbSearchEntitiesAction(this.con, Datamodel.SITE_WIKIDATA);

    }

    public WbSearchEntitiesResult search(String searchText) throws MediaWikiApiErrorException {
        System.out.println("search text is : " + searchText );


        WikibaseDataFetcher wbdf = WikibaseDataFetcher.getWikidataDataFetcher();

        List<WbSearchEntitiesResult> results = wbdf.searchEntities(searchText.trim(),"en");

        System.out.println(results.size());
        System.out.println(results);
        if(results.size() > 0){
            return results.get(0);
        }else{
            return null;
        }
    }

    public ItemDocument getEntity(String entityId) throws MediaWikiApiErrorException {
        System.out.println("entity id is : " + entityId );

        WikibaseDataFetcher wbdf = WikibaseDataFetcher.getWikidataDataFetcher();

        //StatementDocument results = (StatementDocument)wbdf.getEntityDocument(entityId);
        EntityDocument  qEntity = wbdf.getEntityDocument(entityId);
        if (qEntity instanceof ItemDocument) {
            //System.out.println("The English name for entity is " + ((ItemDocument) qEntity).getLabels().get("en").getText());
            Statement st = ((ItemDocument) qEntity).findStatement("P18");
            //System.out.print("statemeent value is "+ st.getClaim().getMainSnak().getValue().toString());
            //System.out.println("En ddesc " + ((ItemDocument) qEntity).getDescriptions().get("en").getText());
        }
        return (ItemDocument) qEntity;
    }


}
