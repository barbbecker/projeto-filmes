package br.com.projetofilmes.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.projetofilmes.domain.Filme;

@Repository
public interface FilmeRepository extends JpaRepository<Filme, Integer> {

	@Query("SELECT f FROM Filme f WHERE f.titulo = :titulo")
	public Optional<Filme> findByTitulo(@Param("titulo") String titulo);

	@Query("SELECT f FROM Filme f INNER JOIN Avaliacao av ON f.id = av.filme.id AND av.usuario.id != :usuarioId")
	public Optional<List<Filme>> getIndicacaoFilme(@Param("usuarioId") Integer usuarioId);
}
