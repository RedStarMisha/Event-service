package ru.practicum.explorewithme.privatenode;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.explorewithme.privatenode.model.NewEventDto;
import ru.practicum.explorewithme.privatenode.model.UpdateEventRequest;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class PrivateController {

    @GetMapping("/{userId}/events")
    public ResponseEntity<Object> getEventsByOwnerId(@PathVariable(name = "userId") Long userId,
                                                     @RequestParam(name = "from", defaultValue = "0") int from,
                                                     @RequestParam(name = "size", defaultValue = "10") int size) {

    }

    @PatchMapping("/{userId}/events")
    public ResponseEntity<Object> updateEvent(@PathVariable(name = "userId") Long userId,
                                              @RequestBody @Valid UpdateEventRequest request) {

    }

    @PostMapping("/{userId}/events")
    public ResponseEntity<Object> addEvent(@PathVariable(name = "userId") Long userId,
                                           @RequestBody NewEventDto newEventDto) {

    }

}
