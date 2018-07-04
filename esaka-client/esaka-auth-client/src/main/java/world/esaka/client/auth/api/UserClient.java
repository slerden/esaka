package world.esaka.client.auth.api;

import world.esaka.client.auth.model.UserView;

public interface UserClient {
    UserView identificateByToken(String token);
}
