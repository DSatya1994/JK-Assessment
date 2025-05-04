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
