package com.boun.semanticweb.model;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "gameUserWords")
public class GameUserWords {
    private Long gameUserWordId;
    private Long gameUserId;
    private Long wordId;
    private Date createdDate;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getGameUserWordId() {
        return gameUserWordId;
    }

    public void setGameUserWordId(Long gameUserWordId) {
        this.gameUserWordId = gameUserWordId;
    }

    public Long getGameUserId() {
        return gameUserId;
    }

    public void setGameUserId(Long gameUserId) {
        this.gameUserId = gameUserId;
    }

    public Long getWordId() {
        return wordId;
    }

    public void setWordId(Long wordId) {
        this.wordId = wordId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
