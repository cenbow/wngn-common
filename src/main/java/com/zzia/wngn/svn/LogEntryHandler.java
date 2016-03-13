package com.zzia.wngn.svn;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.tmatesoft.svn.core.ISVNLogEntryHandler;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNLogEntryPath;

public class LogEntryHandler implements ISVNLogEntryHandler {

    private List<Revision> versionList;

    private String url;

    public LogEntryHandler(String url) {
        this.url = url;
    }

    @Override
    public void handleLogEntry(SVNLogEntry logEntry) throws SVNException {
        if (versionList == null) {
            versionList = new ArrayList<Revision>();
        }
        Revision version = new Revision();
        version.setUrl(this.url);
        version.setAuthor(logEntry.getAuthor());
        version.setMessage(logEntry.getMessage());
        version.setRevision(logEntry.getRevision() + "");
        List<String> pathList = new ArrayList<String>();
        Map<String, SVNLogEntryPath> changedPaths = logEntry.getChangedPaths();
        for (Map.Entry<String, SVNLogEntryPath> entry : changedPaths.entrySet()) {
            SVNLogEntryPath changedPath = entry.getValue();
            pathList.add(changedPath.getType() + ":" + changedPath.getPath() + " " + changedPath.getKind());
        }
        version.setChangedPath(pathList);
        version.setCommitDate(logEntry.getDate());
        versionList.add(version);
    }

    public List<Revision> getVersionList() {
        return versionList;
    }

}
