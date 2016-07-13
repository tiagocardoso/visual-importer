package org.nuxeo.workshop.visualimporter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nuxeo.ecm.core.api.Blob;
import org.nuxeo.ecm.core.api.CoreSession;
import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.platform.filemanager.service.extension.AbstractFileImporter;
import org.nuxeo.ecm.platform.types.TypeManager;

import java.io.IOException;
import java.io.Serializable;

/**
 * Created by tiago on 13-07-2016.
 */
public class VisualImporter extends AbstractFileImporter {

    private static final long serialVersionUID = 1876876876L;

    private static final Log log = LogFactory.getLog(VisualImporter.class);

    @Override
    public DocumentModel create(CoreSession session, Blob content, String path,
                                boolean overwrite, String filename, TypeManager typeService)
            throws  IOException {

        DocumentModel doc = createDocType(session, path, content, filename,"TAVisual");
        return session.createDocument(doc);
    }

    private DocumentModel createDocType(CoreSession session, String path, Blob content, String filename, String type) {
        DocumentModel doc = session.createDocumentModel(path,type,type);
        doc.setPropertyValue("dc:title",filename);
        doc.setPropertyValue("file:content", (Serializable) content);
        doc.setPropertyValue("file:filename",filename);
        return doc;
    }
}
