package com.zzia.wngn.svn;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;

import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.fs.FSRepositoryFactory;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.internal.wc.DefaultSVNOptions;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNInfo;
import org.tmatesoft.svn.core.wc.SVNLogClient;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNUpdateClient;
import org.tmatesoft.svn.core.wc.SVNWCClient;

@SuppressWarnings("deprecation")
public class SVNOperation {

    public static void doCheckout(String url, String username, String password, String dist, Long revision)
            throws SVNException {
        setupLibrary();
        DefaultSVNOptions options = new DefaultSVNOptions();
        SVNClientManager manager = SVNClientManager.newInstance(options, username, password);
        SVNUpdateClient client = manager.getUpdateClient();
        SVNRevision svnRevision = null;
        if (revision == null) {
            svnRevision = SVNRevision.HEAD;
        } else {
            svnRevision = SVNRevision.create(revision);
        }
        client.setIgnoreExternals(true);
        client.setEventHandler(new UpdateEventHandler());
        client.doCheckout(SVNURL.parseURIDecoded(url), new File(dist), SVNRevision.HEAD, svnRevision, SVNDepth.INFINITY,
                false);

    }

    public static void doExport(String url, String username, String password, String dist, Long revision)
            throws SVNException {
        setupLibrary();
        DefaultSVNOptions options = new DefaultSVNOptions();
        SVNClientManager manager = SVNClientManager.newInstance(options, username, password);
        UpdateEventHandler svnEventHandler = new UpdateEventHandler();
        SVNUpdateClient client = manager.getUpdateClient();
        SVNRevision svnRevision = null;
        if (revision == null) {
            svnRevision = SVNRevision.HEAD;
        } else {
            svnRevision = SVNRevision.create(revision);
        }
        client.setIgnoreExternals(true);
        client.setEventHandler(svnEventHandler);
        client.doExport(SVNURL.parseURIDecoded(url), new File(dist), SVNRevision.HEAD, svnRevision, null, true, true);
    }

    public static List<String> doList(String url, String username, String password, Long revision) throws SVNException {
        setupLibrary();
        DefaultSVNOptions options = new DefaultSVNOptions();
        SVNClientManager manager = SVNClientManager.newInstance(options, username, password);
        SVNLogClient logClient = manager.getLogClient();
        DirEntryHandler handler = new DirEntryHandler();
        SVNRevision svnRevision = null;
        if (revision == null) {
            svnRevision = SVNRevision.HEAD;
        } else {
            svnRevision = SVNRevision.create(revision);
        }
        logClient.doList(SVNURL.parseURIDecoded(url), SVNRevision.HEAD, svnRevision, false, true, handler);
        return handler.getFileList();
    }

    public static List<Revision> doLog(String url, String username, String password, int start, int limit)
            throws SVNException {
        setupLibrary();
        DefaultSVNOptions options = new DefaultSVNOptions();
        SVNClientManager manager = SVNClientManager.newInstance(options, username, password);
        SVNLogClient logClient = manager.getLogClient();
        LogEntryHandler handler = new LogEntryHandler(url);
        RevisionEntryHandler revisionHandler = new RevisionEntryHandler();
        logClient.doLog(SVNURL.parseURIDecoded(url), null, SVNRevision.HEAD, null, null, false, false, -1,
                revisionHandler);
        SVNRevision startRevision = revisionHandler.getStartRevision(start, limit);
        if (startRevision == null) {
            return null;
        }
        logClient.doLog(SVNURL.parseURIDecoded(url), null, SVNRevision.HEAD, startRevision,
                revisionHandler.getEndRevision(start, limit), true, true, -1, handler);
        return handler.getVersionList();
    }

    public static String doInfo(String url, String username, String password) throws SVNException {
        setupLibrary();
        DefaultSVNOptions options = new DefaultSVNOptions();
        SVNClientManager manager = SVNClientManager.newInstance(options, username, password);
        SVNURL repositoryBaseUrl = SVNURL.parseURIDecoded(url);
        SVNWCClient client = manager.getWCClient();
        SVNInfo doInfo = client.doInfo(repositoryBaseUrl, SVNRevision.HEAD, SVNRevision.HEAD);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return doInfo.getRevision() + " " + doInfo.getCommittedRevision() + " " + doInfo.getAuthor() + " "
                + sdf.format(doInfo.getCommittedDate()) + " " + doInfo.getPath();
    }

    public static String doStatus() {
        setupLibrary();
        return null;
    }

    /*
     * 初始化库
     */
    private static void setupLibrary() {
        DAVRepositoryFactory.setup();
        SVNRepositoryFactoryImpl.setup();  
        FSRepositoryFactory.setup();  
    }
}