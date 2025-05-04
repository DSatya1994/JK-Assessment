@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentSearchRequest {
    private String author;
    private String docType;
    private LocalDate startDate;
    private LocalDate endDate;

    private int page = 0;
    private int size = 10;
    private String sortBy = "uploadDate";
    private String sortDir = "desc";
}
