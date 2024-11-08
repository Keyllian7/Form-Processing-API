package projeto.com.br.form_processing.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String funcao;
    private String telefone;

    @OneToOne
    @JoinColumn(name = "endereco_id")  // Define a chave estrangeira
    private Endereco endereco;

    // Getters e Setters
}
