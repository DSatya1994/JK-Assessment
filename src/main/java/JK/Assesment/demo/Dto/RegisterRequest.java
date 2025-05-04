public class RegisterRequest {
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @NotEmpty
    private Set<String> roles; // ADMIN, EDITOR, VIEWER
}
