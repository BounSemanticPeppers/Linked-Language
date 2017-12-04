package com.boun.semanticweb.model;

import javax.persistence.*;

@Entity
@Table(name = "wordRelation")
public class WordRelation {
    private Long id;
    private Long sourceWordId;
    private Long targetWordId;
    private Integer score;

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

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
