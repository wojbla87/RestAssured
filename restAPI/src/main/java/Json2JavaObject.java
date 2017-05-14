import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static io.restassured.RestAssured.get;

/**
 * Class maps JSON String to Java POJO
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

        removeAllCommentObjectWithoutPostIdField1(commentsCopy1);

        //Filter all objects from List commentsCopy2 does not have in body keyword="non"

        removeAllCommentObjectsWithoutNonStringInBody(commentsCopy2);

    }

    private static void removeAllCommentObjectsWithoutNonStringInBody(List<Comment> comments) {
        Predicate<Comment> commentPredicate2 = c -> (!c.getBody().contains("non"));
        comments.removeIf(commentPredicate2);
    }

    private static void removeAllCommentObjectWithoutPostIdField1(List<Comment> comments) {
        Predicate<Comment> commentPredicate1 = c -> c.getPostId() != 1;
        comments.removeIf(commentPredicate1);
    }
}