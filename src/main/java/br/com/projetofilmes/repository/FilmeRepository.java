package br.com.projetofilmes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.projetofilmes.domain.Filme;

@Repository
public interface FilmeRepository extends JpaRepository<Filme, Integer> {

}
