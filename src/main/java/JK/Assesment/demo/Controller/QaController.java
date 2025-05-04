@RestController
@RequestMapping("/api/qa")
@RequiredArgsConstructor
public class QaController {

    private final QaService qaService;

    @PostMapping("/ask")
    public ResponseEntity<List<AnswerSnippet>> askQuestion(@RequestBody QuestionRequest request) {
        List<AnswerSnippet> answers = qaService.getAnswers(request);
        return ResponseEntity.ok(answers);
    }
}
