package ru.practicum.explorewithme.admin;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.clients.server.admin.CompilationsClient;
import ru.practicum.explorewithme.models.compilation.NewCompilationDto;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin/compilations")
@AllArgsConstructor(onConstructor_ = @Autowired)
@Validated
public class CompilationsController {
    private final CompilationsClient client;

    @PostMapping
    public ResponseEntity<Object> addCompilation(@RequestBody @Valid NewCompilationDto compilationDto) {
        return client.addCompilation(compilationDto);
    }

    @DeleteMapping("/{compId}")
    public ResponseEntity<Object> deleteCompilation(@PathVariable(name = "compId") Long compId) {
        return client.deleteCompilation(compId);
    }

    @DeleteMapping("/{compId}/events/{eventId}")
    public ResponseEntity<Object> deleteEventFromCompilation(@PathVariable(name = "compId") Long compId,
                                                             @PathVariable(name = "eventId") Long eventId) {
        return client.deleteEventFromCompilation(compId, eventId);
    }

    @PatchMapping("/{compId}/events/{eventId}")
    public ResponseEntity<Object> addEventToCompilation(@PathVariable(name = "compId") Long compId,
                                                        @PathVariable(name = "eventId") Long eventId) {
        return client.addEventFromCompilation(compId, eventId);
    }

    @DeleteMapping("/{compId}/pin")
    public ResponseEntity<Object> unpinCompilation(@PathVariable(name = "compId") Long compId) {
        return client.unpinCompilation(compId);
    }

    @PatchMapping("/{compId}/pin")
    public ResponseEntity<Object> pinCompilation(@PathVariable(name = "compId") Long compId) {
        return client.pinCompilation(compId);
    }
}
