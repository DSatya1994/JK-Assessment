@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AnswerSnippet {
    private Long documentId;
    private String filename;
    private String snippet;
    private String author;
    private LocalDate uploadDate;
}
