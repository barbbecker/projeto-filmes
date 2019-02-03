package br.com.projetofilmes.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.projetofilmes.domain.Genero;

@Repository
public interface GeneroRepository extends JpaRepository<Genero, Integer> {

	@Query("SELECT g FROM Genero g WHERE g.nome LIKE %:nome% ORDER BY g.nome")
	public Optional<Genero> findByName(@Param("nome") String nome);
}
