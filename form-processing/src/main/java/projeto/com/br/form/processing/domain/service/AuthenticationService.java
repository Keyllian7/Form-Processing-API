package projeto.com.br.form.processing.domain.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import projeto.com.br.form.processing.api.dto.user.AuthenticationDTO;
import projeto.com.br.form.processing.api.dto.user.LoginResponseDTO;
import projeto.com.br.form.processing.api.dto.user.RegisterDTO;
import projeto.com.br.form.processing.domain.model.user.User;
import projeto.com.br.form.processing.domain.model.user.UserRole;
import projeto.com.br.form.processing.domain.repository.UserRepository;

import java.util.Map;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ModelMapper modelMapper;

    public LoginResponseDTO login(AuthenticationDTO data) throws Exception {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);
        var token = tokenService.generateToken((User) auth.getPrincipal());
        return new LoginResponseDTO(token);
    }

    public Map<String, String> register(RegisterDTO data) {
        if (this.userRepository.findByEmail(data.email()) != null) {
            return Map.of("Error", "O e-mail fornecido já está em uso.");
        }

        if (data.name() == null || data.email() == null || data.password() == null) {
            return Map.of("Error", "Todos os campos obrigatórios devem ser preenchidos.");
        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        User newUser = modelMapper.map(data, User.class);
        newUser.setPassword(encryptedPassword);
        newUser.setRole(UserRole.USER);
        this.userRepository.save(newUser);

        return Map.of("Message", "Usuário registrado com sucesso!");
    }
}