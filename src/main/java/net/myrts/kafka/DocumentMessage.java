package net.myrts.kafka;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:vzhovtiuk@gmail.com">Vitaliy Zhovtyuk</a>
 *         Date: 4/28/16
 *         Time: 11:06 AM
 */
public class DocumentMessage {
    private String documentId;
    private String documentBody;

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public String getDocumentBody() {
        return documentBody;
    }

    public void setDocumentBody(String documentBody) {
        this.documentBody = documentBody;
    }


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("DocumentMessage{");
        sb.append("documentId='").append(documentId).append('\'');
        sb.append(", documentBody='").append(documentBody).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
