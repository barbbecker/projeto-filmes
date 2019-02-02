package br.com.projetofilmes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.projetofilmes.domain.Genero;

@Repository
public interface GeneroRepository extends JpaRepository<Genero, Integer> {

}
