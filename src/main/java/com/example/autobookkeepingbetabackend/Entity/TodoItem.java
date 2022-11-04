package com.example.autobookkeepingbetabackend.Entity;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
public class TodoItem implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String itemTitle;

    @Column
    private String postTime;

    @Column
    private String posterId;

    @Column
    private String handlerId;

    @Column
    private String posterPortrait;

    @Column
    private String handlerPortrait;

    @Column
    private Boolean isFinished;

    @Column
    private String handleTime;

    @Column
    private String familyId;

    public TodoItem() {}

    public TodoItem(String itemTitle, String postTime, String posterId, String handlerId, String posterPortrait, String handlerPortrait, Boolean isFinished, String handleTime, String familyId) {
        this.itemTitle = itemTitle;
        this.postTime = postTime;
        this.posterId = posterId;
        this.handlerId = handlerId;
        this.posterPortrait = posterPortrait;
        this.handlerPortrait = handlerPortrait;
        this.isFinished = isFinished;
        this.handleTime = handleTime;
        this.familyId = familyId;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    public String getPosterId() {
        return posterId;
    }

    public void setPosterId(String posterId) {
        this.posterId = posterId;
    }

    public String getHandlerId() {
        return handlerId;
    }

    public void setHandlerId(String handlerId) {
        this.handlerId = handlerId;
    }

    public String getPosterPortrait() {
        return posterPortrait;
    }

    public void setPosterPortrait(String posterPortrait) {
        this.posterPortrait = posterPortrait;
    }

    public String getHandlerPortrait() {
        return handlerPortrait;
    }

    public void setHandlerPortrait(String handlerPortrait) {
        this.handlerPortrait = handlerPortrait;
    }

    public Boolean getFinished() {
        return isFinished;
    }

    public void setFinished(Boolean finished) {
        isFinished = finished;
    }

    public String getHandleTime() {
        return handleTime;
    }

    public void setHandleTime(String handleTime) {
        this.handleTime = handleTime;
    }

    public String getFamilyId() {
        return familyId;
    }

    public void setFamilyId(String familyId) {
        this.familyId = familyId;
    }
}
