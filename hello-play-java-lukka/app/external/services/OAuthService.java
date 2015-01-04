package external.services;

import com.fasterxml.jackson.databind.JsonNode;

import play.libs.F.Promise;
import play.libs.F.Tuple;
import play.libs.OAuth.RequestToken;

public interface OAuthService {

	Tuple<String, RequestToken> retrieveRequestToken(String callbackUrl);
	
	Promise<JsonNode> registeredUserProfile(RequestToken token, String authVerifier);
}
