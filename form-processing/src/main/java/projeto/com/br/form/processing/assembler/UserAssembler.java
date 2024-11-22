package projeto.com.br.form.processing.assembler;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import projeto.com.br.form.processing.api.dto.userDTO.UserInputDTO;
import projeto.com.br.form.processing.api.dto.userDTO.UserLoginDTO;
import projeto.com.br.form.processing.api.dto.userDTO.UserOutDTO;
import projeto.com.br.form.processing.domain.model.user.User;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Component
public class UserAssembler {

    private ModelMapper modelMapper;

    public User paraUser(final UserInputDTO userInputDTO) {
        return modelMapper.map(userInputDTO, User.class);
    }

    public UserOutDTO paraUserDTO(final User user) {
        return modelMapper.map(user, UserOutDTO.class);
    }

    public User paraUser(final UserLoginDTO userLoginDTO) {
        User user = new User();
        user.setEmail(userLoginDTO.email());
        user.setSenha(userLoginDTO.senha());
        return user;
    }

    public List<UserOutDTO> paraColecaoUserDTO(final List<User> users) {
        return users.stream().map(this::paraUserDTO).collect(Collectors.toList());
    }

}
