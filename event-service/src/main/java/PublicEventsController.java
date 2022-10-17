import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PublicEventsController {

    @GetMapping("/compilations")
    public ResponseEntity<Object> getEvents() {
        return null;
    }

}
