package projeto.com.br.form.processing.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import projeto.com.br.form.processing.domain.model.form.Form;
import projeto.com.br.form.processing.domain.model.user.User;
import projeto.com.br.form.processing.domain.repository.FormRepository;
import projeto.com.br.form.processing.domain.repository.UserRepository;
import projeto.com.br.form.processing.domain.service.TokenService;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private FormRepository formRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    public String updateUser(String id, User updatedUser) {
        Optional<User> existingUserOpt = userRepository.findById(id);
        if (existingUserOpt.isEmpty()) {
            return "Usuário não encontrado.";
        }

        User existingUser = existingUserOpt.get();

        if (updatedUser.getName() != null && !updatedUser.getName().isEmpty()) {
            existingUser.setName(updatedUser.getName());
        }
        if (updatedUser.getEmail() != null && !updatedUser.getEmail().isEmpty()) {
            existingUser.setEmail(updatedUser.getEmail());
        }
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            String encryptedPassword = passwordEncoder.encode(updatedUser.getPassword());
            existingUser.setPassword(encryptedPassword);
        }

        userRepository.save(existingUser);
        return "Usuário atualizado com sucesso.";
    }

    public String deleteUser(String id, String token) {
        try {
            String email = tokenService.validadeToken(token.replace("Bearer ", ""));
            if (email.isEmpty()) {
                return "Token inválido ou expirado.";
            }

            User authenticatedUser = (User) userRepository.findByEmail(email);
            if (authenticatedUser == null) {
                return "Usuário autenticado não encontrado.";
            }

            if (!authenticatedUser.getId().equals(id)) {
                return "Você não tem permissão para excluir esta conta.";
            }

            List<Form> userForms = formRepository.findByEmissorId(id);
            if (userForms != null && !userForms.isEmpty()) {
                return "Não é possível excluir a conta, pois existem formulários associados a ela.";
            }

            userRepository.delete(authenticatedUser);
            return "Conta excluída com sucesso.";
        } catch (Exception e) {
            return "Erro ao processar a solicitação: " + e.getMessage();
        }
    }

    public List<Form> listFormsByUser(String id) {
        return formRepository.findByEmissorId(id);
    }
}
