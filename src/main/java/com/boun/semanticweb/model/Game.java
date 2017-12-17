package com.boun.semanticweb.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "game")
public class Game implements Serializable {
    private Long gameId;
    private Boolean complete;
    private Integer score;
    private Date createdDate;
    private Long createdBy;
    private Long askedWordId;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Boolean getComplete() {
        return complete;
    }

    public void setComplete(Boolean complete) {
        this.complete = complete;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getAskedWordId() {
        return askedWordId;
    }

    public void setAskedWordId(Long askedWordId) {
        this.askedWordId = askedWordId;
    }
}
