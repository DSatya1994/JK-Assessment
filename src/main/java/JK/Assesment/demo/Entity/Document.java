@Entity
@Table(name = "documents")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String filename;

    private String contentType;

    @Column(length = 100000)
    private String content;

    private String author;

    private LocalDate uploadDate;

    private String docType; // PDF, DOCX, TXT, etc.
}
