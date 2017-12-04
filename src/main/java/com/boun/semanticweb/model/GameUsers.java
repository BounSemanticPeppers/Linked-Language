package com.boun.semanticweb.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "gameUsers")
public class GameUsers {
    private Long gameUserId;
    private Long gameId;
    private Long userId;
    private Date createdDate;
    private Boolean finished;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getGameUserId() {
        return gameUserId;
    }

    public void setGameUserId(Long gameUserId) {
        this.gameUserId = gameUserId;
    }

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }
}
