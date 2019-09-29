import java.text.ParseException;
import java.util.List;

public interface TripApi {
    List<List<Route>> getRoute(String data) throws ParseException;
}