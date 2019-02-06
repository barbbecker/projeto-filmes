package br.com.projetofilmes.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.projetofilmes.domain.Avaliacao;

@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Integer> {

	 @Query("SELECT a FROM Avaliacao a WHERE a.usuario.id = :idUsuario AND a.filme.id = :filmeId")
	 public Optional<Avaliacao> findByUserEmailAndFilmeId(@Param("idUsuario") Integer idUsuario,
	 		@Param("filmeId") Integer filmeId);

}
