package senai499.com.br.saep.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import senai499.com.br.saep.model.ClientesModel;

public interface ClientesRepository
        extends JpaRepository<ClientesModel, Long> {

    Optional<ClientesModel> findByUsuarioId(Long usuarioId);

    boolean existsByCpf(String cpf);
}