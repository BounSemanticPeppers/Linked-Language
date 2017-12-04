package com.boun.semanticweb.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "wordRelationHistory")
public class WordRelationHistory {
    private Long id;
    private Long sourceWordId;
    private Long targetWordId;
    private Date createdDate;
    private Long gameId;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSourceWordId() {
        return sourceWordId;
    }

    public void setSourceWordId(Long sourceWordId) {
        this.sourceWordId = sourceWordId;
    }

    public Long getTargetWordId() {
        return targetWordId;
    }

    public void setTargetWordId(Long targetWordId) {
        this.targetWordId = targetWordId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }
}
