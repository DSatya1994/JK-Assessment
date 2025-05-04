@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void registerUser(RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username already taken");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Set<Role> userRoles = new HashSet<>();
        for (String roleName : request.getRoles()) {
            Role role = roleRepository.findByName(roleName.toUpperCase())
                    .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
            userRoles.add(role);
        }

        user.setRoles(userRoles);
        userRepository.save(user);
    }
}
