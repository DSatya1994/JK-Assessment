@Service
@RequiredArgsConstructor
public class QaServiceImpl implements QaService {

    private final DocumentRepository documentRepository;

    @Override
    public List<AnswerSnippet> getAnswers(QuestionRequest questionRequest) {
        List<Document> docs = documentRepository.searchByKeyword(
                questionRequest.getQuestion(),
                PageRequest.of(0, questionRequest.getLimit())
        );

        return docs.stream()
                .map(doc -> AnswerSnippet.builder()
                        .documentId(doc.getId())
                        .filename(doc.getFilename())
                        .snippet(extractSnippet(doc.getContent(), questionRequest.getQuestion()))
                        .author(doc.getAuthor())
                        .uploadDate(doc.getUploadDate())
                        .build())
                .toList();
    }

    private String extractSnippet(String content, String keyword) {
        String lowerContent = content.toLowerCase();
        String lowerKeyword = keyword.toLowerCase();
        int index = lowerContent.indexOf(lowerKeyword);
        if (index == -1) return content.substring(0, Math.min(200, content.length())) + "...";

        int start = Math.max(0, index - 50);
        int end = Math.min(content.length(), index + 100);
        return "... " + content.substring(start, end).replaceAll("\\s+", " ") + " ...";
    }
}
