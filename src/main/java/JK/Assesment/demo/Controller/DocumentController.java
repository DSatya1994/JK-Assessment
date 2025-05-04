@RestController
@RequestMapping("/api/documents")
@RequiredArgsConstructor
public class DocumentController {

    private final DocumentService documentService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadDocument(@RequestParam("file") MultipartFile file,
                                            @RequestParam("author") String author) {
        try {
            Document doc = documentService.uploadDocument(file, author);
            return ResponseEntity.ok(doc);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Failed to upload document: " + e.getMessage());
        }
    }

    @PostMapping("/search")
public ResponseEntity<?> searchDocuments(@RequestBody DocumentSearchRequest request) {
    Page<Document> result = documentService.searchDocuments(request);
    return ResponseEntity.ok(result);
}

}
