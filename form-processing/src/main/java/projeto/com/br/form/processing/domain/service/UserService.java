package projeto.com.br.form.processing.domain.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import projeto.com.br.form.processing.domain.model.user.Roles;
import projeto.com.br.form.processing.domain.model.user.User;
import projeto.com.br.form.processing.domain.repository.UserRepository;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public User register(final User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("Email já está em uso.");
        }
        user.setSenha(passwordEncoder.encode(user.getSenha()));
        user.setRole(Roles.USER);
        return userRepository.save(user);
    }

    @Transactional
    public User atualizar(final User user, UUID userId) {

        User pegarUser = buscar(userId);
        pegarUser.setNome(user.getNome());
        pegarUser.setEmail(user.getEmail());
        pegarUser.setSenha(user.getSenha());
        return userRepository.save(pegarUser);

    }

    @Transactional
    public User buscar(final UUID userId) {
        return userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("Usuario não encontrado"));
    }

    @Transactional
    public List<User> listar() {
        return userRepository.findAll();
    }

}
