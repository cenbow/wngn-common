package com.zzia.wngn.svn;

import java.util.ArrayList;
import java.util.List;

import org.tmatesoft.svn.core.ISVNDirEntryHandler;
import org.tmatesoft.svn.core.SVNDirEntry;
import org.tmatesoft.svn.core.SVNException;

public class DirEntryHandler implements ISVNDirEntryHandler {

    private List<String> fileList;

    @Override
    public void handleDirEntry(SVNDirEntry dirEntry) throws SVNException {

        if (fileList == null) {
            fileList = new ArrayList<String>();
        }
        fileList.add(dirEntry.getRelativePath() + "/" + dirEntry.getName());
    }

    public List<String> getFileList() {
        return fileList;
    }

}