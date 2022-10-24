package ru.practicum.explorewithme.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.explorewithme.server.models.Compilation;

@Repository
public interface CompilationRepository extends JpaRepository<Compilation, Long> {
}
