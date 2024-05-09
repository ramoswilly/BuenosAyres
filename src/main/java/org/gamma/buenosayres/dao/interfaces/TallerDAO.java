package org.gamma.buenosayres.dao.interfaces;

import org.gamma.buenosayres.model.Taller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TallerDAO extends JpaRepository<Taller, UUID> {
}
