package ittalents.dominos.model.repositories;

import ittalents.dominos.model.entities.DoughType;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface DoughTypeRepository extends JpaRepository<DoughType, Integer> {
}
