package com.boun.semanticweb.viewModel;

import com.boun.semanticweb.model.Game;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GameRecord extends Game {

    private String askedWord;

    public GameRecord(Object[] queryResult) throws ParseException {
        SimpleDateFormat sdf =
                new     SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date createdDate = sdf.parse(queryResult[3].toString());

        this.setGameId(((Integer)queryResult[0]).longValue());
        this.setAskedWordId(((Integer)queryResult[1]).longValue());
        this.setComplete((Boolean)queryResult[2]);
        this.setCreatedDate(createdDate);
        this.setCreatedBy(((Integer)queryResult[4]).longValue());
        this.setScore((Integer)queryResult[5]);
        this.askedWord = queryResult[6].toString();
    }


}
