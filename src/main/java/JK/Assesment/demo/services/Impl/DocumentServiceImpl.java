@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;
    private final Tika tika = new Tika();

    @Override
    public Document uploadDocument(MultipartFile file, String author) throws IOException {
        String filename = file.getOriginalFilename();
        String contentType = file.getContentType();
        String parsedContent = tika.parseToString(file.getInputStream());

        Document document = Document.builder()
                .filename(filename)
                .contentType(contentType)
                .content(parsedContent)
                .author(author)
                .uploadDate(LocalDate.now())
                .docType(detectDocType(filename))
                .build();

        return documentRepository.save(document);
    }
    
    @Override
public Page<Document> searchDocuments(DocumentSearchRequest request) {
    Pageable pageable = PageRequest.of(
            request.getPage(),
            request.getSize(),
            Sort.by(Sort.Direction.fromString(request.getSortDir()), request.getSortBy())
    );

    Specification<Document> spec = Specification.where(null);

    if (request.getAuthor() != null) {
        spec = spec.and((root, query, cb) ->
            cb.like(cb.lower(root.get("author")), "%" + request.getAuthor().toLowerCase() + "%"));
    }

    if (request.getDocType() != null) {
        spec = spec.and((root, query, cb) ->
            cb.equal(cb.lower(root.get("docType")), request.getDocType().toLowerCase()));
    }

    if (request.getStartDate() != null) {
        spec = spec.and((root, query, cb) ->
            cb.greaterThanOrEqualTo(root.get("uploadDate"), request.getStartDate()));
    }

    if (request.getEndDate() != null) {
        spec = spec.and((root, query, cb) ->
            cb.lessThanOrEqualTo(root.get("uploadDate"), request.getEndDate()));
    }

    return documentRepository.findAll(spec, pageable);
}


    private String detectDocType(String filename) {
        if (filename == null) return "UNKNOWN";
        String extension = filename.substring(filename.lastIndexOf('.') + 1).toLowerCase();
        return switch (extension) {
            case "pdf" -> "PDF";
            case "doc", "docx" -> "DOCX";
            case "txt" -> "TXT";
            default -> "UNKNOWN";
        };
    }
}
