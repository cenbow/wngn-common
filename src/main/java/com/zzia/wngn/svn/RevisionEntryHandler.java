package com.zzia.wngn.svn;

import java.util.LinkedHashMap;
import java.util.Map;

import org.tmatesoft.svn.core.ISVNLogEntryHandler;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.wc.SVNRevision;

public class RevisionEntryHandler implements ISVNLogEntryHandler {

    private Map<Integer, Long> revisionMap;
    private int count = 0;

    @Override
    public void handleLogEntry(SVNLogEntry logEntry) throws SVNException {
        if (this.revisionMap == null) {
            this.revisionMap = new LinkedHashMap<Integer,Long>();
        }
        this.revisionMap.put(this.count++, logEntry.getRevision());
    }
    
    public SVNRevision getStartRevision(int start,int limit){
        int startVersion = start - 1;  
        if(startVersion > count){
            return null;
        }
        return SVNRevision.create(revisionMap.get(startVersion));
    }
    
    public SVNRevision getEndRevision(int start,int limit){
        int endVersion = start + limit -1 -1;
        if(endVersion > count){
            endVersion = count - 1;
        }
        return SVNRevision.create(revisionMap.get(endVersion));
        
    }

    public Map<Integer, Long> getRevisionMap() {
        return revisionMap;
    }
    
    
}
