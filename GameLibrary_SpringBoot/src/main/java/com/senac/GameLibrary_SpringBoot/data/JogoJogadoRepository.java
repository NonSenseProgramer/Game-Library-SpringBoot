package com.senac.GameLibrary_SpringBoot.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface JogoJogadoRepository extends JpaRepository <jogoJogado, JogosJogadosId> {
    
}
