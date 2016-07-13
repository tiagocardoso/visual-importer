package org.nuxeo.workshop.visualimporter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nuxeo.ecm.core.api.Blob;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.PathRef;
import org.nuxeo.ecm.core.api.pathsegment.PathSegmentService;
import org.nuxeo.ecm.platform.filemanager.service.extension.AbstractFileImporter;
import org.nuxeo.ecm.platform.filemanager.utils.FileManagerUtils;
import org.nuxeo.ecm.platform.types.TypeManager;
import org.nuxeo.runtime.api.Framework;

import java.io.IOException;

/**
 * Created by tiago on 13-07-2016.
 */
public class VisualImporter extends AbstractFileImporter {

    private static final Log log = LogFactory.getLog(VisualImporter.class);

    @Override
    public DocumentModel create(CoreSession session, Blob content, String path,
                                boolean overwrite, String filename, TypeManager typeService)
            throws IOException {
        DocumentModel visual = importVisual(session, content, path);
        visual = session.createDocument(visual);
        return visual;
    }

    private DocumentModel importVisual(CoreSession session, Blob blob, String path) {
        // create document TAVisual
        DocumentModel container = session.getDocument(new PathRef(path));
        String docType = getDocType(container); // from override or descriptor
        if (docType == null) {
            docType = getDefaultDocType();
        }
        String title = FileManagerUtils.fetchTitle(blob.getFilename());
        DocumentModel doc = session.createDocumentModel(docType);
        doc.setPropertyValue("dc:title", title);
        // set path
        PathSegmentService pss = Framework.getLocalService(PathSegmentService.class);
        doc.setPathInfo(path, pss.generatePathSegment(doc));
        // update data
        updateDocument(doc, blob);
        return doc;
    }

    @Override
    public String getDefaultDocType() {
        return "TAVisual";
    }

    @Override
    public boolean isOverwriteByTitle() {
        return true;
    }
}
