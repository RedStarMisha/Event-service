package ru.practicum.explorewithme.clients.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.practicum.explorewithme.clients.BaseClient;
import ru.practicum.explorewithme.clients.model.NewUserRequest;

import java.util.Map;

public class UserClient extends BaseClient {
    private static final String USERS = "/users";


    public UserClient(RestTemplate rest) {
        super(rest);
    }

     public ResponseEntity<Object> getUsers(long[] ids, int from, int size) {
        Map<String, Object> param = Map.of(
                "ids", ids,
                "from", from,
                "size", size
        );
        return get("", param);
    }

    public ResponseEntity<Object> addUser(NewUserRequest newUserRequest) {
        return post("", newUserRequest);
    }

    public ResponseEntity<Object> deleteUser(long userId) {
        return delete("/" + userId);
    }
}
