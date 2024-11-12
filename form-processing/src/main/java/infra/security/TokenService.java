package infra.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import domain.model.Usuario;

@Service
public class TokenService {
    @Value("${token.senha}")
    private String senha;

    public String gerarToken(Usuario usuario){
        try {
            Algorithm algoritimo = Algorithm.HMAC256(senha);

            String token = JWT.create()
                .withIssuer("form-processing-api")
                .withSubject(usuario.getEmail())
                .withExpiresAt(gerarTempoExpiracao())
                .sign(algoritimo);
            return token;
        } catch (JWTCreationException exception) {
            throw new RuntimeException("Erro enquanto está autenticando");
        }
    }

        public String validarToken(String token){
            try {
                Algorithm algoritimo = Algorithm.HMAC256(senha);
                return JWT.require(algoritimo)
                    .withIssuer("form-processing-api")
                    .build()
                    .verify(token)
                    .getSubject();
            } catch (JWTVerificationException exception) {
                return null;
            }
        }

    private Instant gerarTempoExpiracao() {
        return LocalDateTime.now().plusHours(1).toInstant(ZoneOffset.of("-03:00"));
    }
}
