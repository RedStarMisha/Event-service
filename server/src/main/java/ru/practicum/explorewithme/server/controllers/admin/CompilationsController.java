package ru.practicum.explorewithme.server.controllers.admin;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.models.compilation.CompilationDto;
import ru.practicum.explorewithme.models.compilation.NewCompilationDto;
import ru.practicum.explorewithme.server.services.admin.CompilationService;

@RestController
@RequestMapping("/admin/compilations")
@AllArgsConstructor(onConstructor_ = @Autowired)
public class CompilationsController {
    private final CompilationService service;

    @PostMapping
    public CompilationDto addCompilation(@RequestBody NewCompilationDto compilationDto) {
        return service.addCompilation(compilationDto);
    }

    @DeleteMapping("/{compId}")
    public void deleteCompilation(@PathVariable(name = "compId") long compId) {
        service.deleteCompilation(compId);
    }

    @DeleteMapping("/{compId}/events/{eventId}")
    public void deleteEventFromCompilation(@PathVariable(name = "compId") long compId,
                                                             @PathVariable(name = "eventId") long eventId) {
        service.deleteEventFromCompilation(compId, eventId);
    }

    @PatchMapping("/{compId}/events/{eventId}")
    public void addEventFromCompilation(@PathVariable(name = "compId") long compId,
                                                          @PathVariable(name = "eventId") long eventId) {
        service.addEventFromCompilation(compId, eventId);
    }

    @DeleteMapping("/{compId}/pin")
    public void unpinCompilation(@PathVariable(name = "compId") long compId) {
        service.unpinCompilation(compId);
    }

    @PatchMapping("/{compId}/pin")
    public void pinCompilation(@PathVariable(name = "compId") long compId) {
        service.pinCompilation(compId);
    }
}
