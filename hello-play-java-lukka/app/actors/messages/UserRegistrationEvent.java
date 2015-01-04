package actors.messages;

import models.RegisteredUser;
import play.libs.Json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class UserRegistrationEvent implements UserEvent {
	
	private final RegisteredUser user;

	public UserRegistrationEvent(RegisteredUser user) {
		this.user = user;
	}
	
	@Override
	public JsonNode json() {
		final ObjectNode result = Json.newObject();
		result.put("messageType", "registeredUser");
		result.put("name", user.name);
		result.put("description", user.description);
		result.put("twitterId", user.twitterId);
		result.put("pictureUrl", user.pictureUrl);
		return result;
	}
	
}
