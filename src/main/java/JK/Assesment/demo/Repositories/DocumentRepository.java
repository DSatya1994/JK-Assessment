@Repository
public interface DocumentRepository extends JpaRepository<Document, Long>, JpaSpecificationExecutor<Document> {

    // Keyword search in content or title
    @Query("SELECT d FROM Document d WHERE LOWER(d.content) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(d.title) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Document> searchByKeyword(@Param("keyword") String keyword);

    // Filtering by metadata (author, type)
    Page<Document> findByAuthorContainingIgnoreCaseAndFileTypeContainingIgnoreCase(String author, String fileType, Pageable pageable);
}
