@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {
        List<String> roles = List.of("ADMIN", "EDITOR", "VIEWER");

        for (String role : roles) {
            if (roleRepository.findByName(role).isEmpty()) {
                roleRepository.save(new Role(null, role));
            }
        }
    }
}
