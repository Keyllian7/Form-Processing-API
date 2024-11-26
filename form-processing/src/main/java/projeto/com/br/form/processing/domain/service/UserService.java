package projeto.com.br.form.processing.domain.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import projeto.com.br.form.processing.domain.model.form.Form;
import projeto.com.br.form.processing.domain.model.user.Roles;
import projeto.com.br.form.processing.domain.model.user.User;
import projeto.com.br.form.processing.domain.repository.FormRepository;
import projeto.com.br.form.processing.domain.repository.UserRepository;
import projeto.com.br.form.processing.infra.security.TokenService;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final TokenService tokenService;
    private final FormRepository formRepository;

    @Transactional
    public User register(final User user) {
        User existingActiveUser = userRepository.findActiveByEmail(user.getEmail());
        if (existingActiveUser != null) {
            throw new IllegalArgumentException("Email já está em uso por um usuário ativo.");
        }
        User existingUser = userRepository.findByEmailUser(user.getEmail());

        if (existingUser != null && existingUser.getDataExclusao() != null) {
            existingUser.setDataExclusao(null);
            existingUser.setSenha(passwordEncoder.encode(user.getSenha()));
            existingUser.setRole(Roles.USER);
            emailService.enviarEmailConfirmacao(
                    existingUser.getEmail(),
                    "Cadastro Reativado com Sucesso!",
                    String.format("Olá, %s! Seu cadastro foi reativado com sucesso. Bem-vindo novamente à nossa plataforma!", existingUser.getNome())
            );
            return userRepository.save(existingUser);
        }

        user.setSenha(passwordEncoder.encode(user.getSenha()));
        user.setRole(Roles.USER);
        emailService.enviarEmailConfirmacao(
                user.getEmail(),
                "Cadastro Realizado com Sucesso!",
                String.format("Olá, %s! Seu cadastro foi realizado com sucesso. Bem-vindo à nossa plataforma!", user.getNome())
        );
        return userRepository.save(user);
    }

    @Transactional
    public User atualizar(final User user, UUID userId) {

        User pegarUser = buscar(userId);
        pegarUser.setNome(user.getNome());
        pegarUser.setEmail(user.getEmail());
        pegarUser.setSenha(passwordEncoder.encode(user.getSenha()));
        return userRepository.save(pegarUser);

    }

    @Transactional
    public User buscar(final UUID userId) {
        return userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("Usuario não encontrado"));
    }

    @Transactional
    public List<User> listar() {
        return userRepository.findAllAtivos();
    }

    @Transactional
    public void deletar(String token) {
        String authenticatedEmail = tokenService.validarToken(token);
        if (authenticatedEmail.isEmpty()) {
            throw new SecurityException("Token inválido ou expirado.");
        }
        User authenticatedUser = (User) userRepository.findByEmail(authenticatedEmail);
        if (authenticatedUser == null) {
            throw new EntityNotFoundException("Usuário autenticado não encontrado.");
        }
        authenticatedUser.setDataExclusao(OffsetDateTime.now());
        userRepository.save(authenticatedUser);

        List<Form> formularios = formRepository.findByCriadorIdAndDataExclusaoIsNull(authenticatedUser.getId());
        formularios.forEach(form -> form.setDataExclusao(OffsetDateTime.now()));
        formRepository.saveAll(formularios);
    }

    public boolean isUserExcluded(String email) {
        User user = (User) userRepository.findByEmail(email);
        return user != null && user.getDataExclusao() != null;
    }

}
