package next.config;

import org.codehaus.jackson.annotate.JsonTypeInfo;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

import next.domain.user.GitHubUser;
import next.domain.user.SrelloUser;
import next.domain.user.UserType;

@JsonTypeInfo(
		use= JsonTypeInfo.Id.NAME,
		include = JsonTypeInfo.As.PROPERTY,
		property = "type"
)
@JsonSubTypes({
	@Type(value = SrelloUser.class, name = UserType.Values.SRELLO),
	@Type(value = GitHubUser.class, name = UserType.Values.GITHUB),
})
public abstract class UserMixin {

}
