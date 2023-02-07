package au.DB_Connector;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import au.SimCard.SimCardInfo;

@Repository
public interface SimCardRepository extends JpaRepository<SimCardInfo, Long> {
    
}
