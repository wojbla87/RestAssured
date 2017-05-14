import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static io.restassured.RestAssured.get;

/**
 * Class maps JSON String to Java POJO and
 * Filter List Comment copies
 * Created by wblachut on 2017-05-12.
 */

public class Json2JavaObject {

    public static void main(String[] args) throws IOException {

        //Serialization Json for POJO Comment object

        String response = get("http://jsonplaceholder.typicode.com/comments").asString();
        ObjectMapper mapper = new ObjectMapper();
        List<Comment> comments =
                mapper.readValue(response,
                        new TypeReference<List<Comment>>() {
                        });

        //Make two copies of comments List

        List<Comment> commentsCopy1 = new ArrayList<>(comments);
        List<Comment> commentsCopy2 = new ArrayList<>(comments);

        //Filter all objects from List commentsCopy1 which does not have postId=1

        commentsCopy1.removeIf(getPostIdNotOnePredicate());

        //Filter all objects from List commentsCopy2 does not have in body keyword="non"

        commentsCopy2.removeIf(getBodyDoesntContainNonPredicate());
    }

    private static Predicate<Comment> getBodyDoesntContainNonPredicate() {
        return c -> (!c.getBody().contains("non"));
    }

    private static Predicate<Comment> getPostIdNotOnePredicate() {
        return c -> c.getPostId() != 1;
    }
}