package com.example.example.db;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 

/**
 * Entity mapped to table "MessageInfo".
 */
public class MessageInfo {

    private String did;
    private Long id;
    private String imgUrl;
    private String message;
    private String time;
    private String title;
    private String type;
    private String uid;
    private Boolean deleteStatus;
    private Boolean readStatus;
    private String videoFileId;
    private String videoUrl;

    public MessageInfo() {
    }

    public MessageInfo(Long id) {
        this.id = id;
    }

    public MessageInfo(String did, Long id, String imgUrl, String message, String time, String title, String type, String uid, Boolean deleteStatus, Boolean readStatus, String videoFileId, String videoUrl) {
        this.did = did;
        this.id = id;
        this.imgUrl = imgUrl;
        this.message = message;
        this.time = time;
        this.title = title;
        this.type = type;
        this.uid = uid;
        this.deleteStatus = deleteStatus;
        this.readStatus = readStatus;
        this.videoFileId = videoFileId;
        this.videoUrl = videoUrl;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Boolean getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(Boolean deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public Boolean getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(Boolean readStatus) {
        this.readStatus = readStatus;
    }

    public String getVideoFileId() {
        return videoFileId;
    }

    public void setVideoFileId(String videoFileId) {
        this.videoFileId = videoFileId;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

}
