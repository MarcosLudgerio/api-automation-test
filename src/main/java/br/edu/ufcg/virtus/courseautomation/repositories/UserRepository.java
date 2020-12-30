package br.edu.ufcg.virtus.courseautomation.repositories;

import br.edu.ufcg.virtus.courseautomation.models.UserApi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public interface UserRepository <T, ID extends Serializable> extends JpaRepository<UserApi, Long> {
}
