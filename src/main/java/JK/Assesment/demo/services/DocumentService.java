public interface DocumentService {
    Document uploadDocument(MultipartFile file, String author) throws IOException;
    Page<Document> searchDocuments(DocumentSearchRequest request);
}