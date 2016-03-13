package com.zzia.wngn.svn;

import java.util.Date;
import java.util.List;

public class Revision {
    private String url;
    private String author;
    private String revision;
    private String message;
    private Date commitDate;
    private List<String> changedPath;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getRevision() {
        return revision;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getCommitDate() {
        return commitDate;
    }

    public void setCommitDate(Date commitDate) {
        this.commitDate = commitDate;
    }

    public List<String> getChangedPath() {
        return changedPath;
    }

    public void setChangedPath(List<String> changedPath) {
        this.changedPath = changedPath;
    }

    @Override
    public String toString() {
        return "VersionInfo [url=" + url + ", author=" + author + ", revision=" + revision + ", message=" + message
                + ", commitDate=" + commitDate + ", changedPath=" + changedPath + "]";
    }

}
