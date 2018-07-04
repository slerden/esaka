package world.esaka.client.auth.impl;

import world.esaka.client.auth.api.UserClient;
import world.esaka.client.auth.model.UserView;

import static org.junit.Assert.*;

public class UserClientDefaultImplTest {

    @org.junit.Test
    public void identificateByToken() {
        UserClient userClient = new UserClientDefaultImpl();
        UserView userView = userClient.identificateByToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c3IiLCJyb2xlIjoiVVNFUiIsImlzcyI6ImVzYWthLWF1dGgiLCJleHAiOjE1MzA1NDkwODAsImlhdCI6MTUzMDQ2MjY4MCwiZW1haWwiOiJrYWZrYUB5YW5kZXgucnUifQ.duHv1fTXWim3xDc-IuX5kEgMIpB2CggZedP2sehndRc");
        System.out.println(userView);
        System.out.println(userView.getUsername());
    }
}