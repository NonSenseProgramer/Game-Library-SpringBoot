package com.senac.GameLibrary_SpringBoot.data;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface jogoRepository extends JpaRepository<Jogo, Long>{
   Jogo findByJogoIgnoreCase(String nome);

}
