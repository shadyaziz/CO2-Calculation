package com.sap.challenge.openroute.adapter;

import static com.sap.challenge.openroute.model.OpenRouteConstant.COORDINATES;
import static com.sap.challenge.openroute.model.OpenRouteConstant.DISTANCES;
import static com.sap.challenge.openroute.model.OpenRouteConstant.FEATURES;
import static com.sap.challenge.openroute.model.OpenRouteConstant.GEOMETRY;
import static com.sap.challenge.openroute.model.OpenRouteConstant.OPENROUTE_URL;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import com.sap.challenge.exception.InvalidValueException;
import com.sap.challenge.openroute.exception.JSONParseException;
import com.sap.challenge.openroute.exception.OpenRouteConnectionException;
import com.sap.challenge.openroute.model.Coordinate;
import com.sap.challenge.openroute.model.DistanceRequestBuilder;

/**
 * OpenRoute Adapter class to communicate with OpenRoute APIs
 *
 */
public class OpenRouteAdapter {
  private final Logger logger = LogManager.getLogger(OpenRouteAdapter.class);

  private static final String JSON_EXCEPTION = "JSON parsing error , cannot find {}";
  private static final String ORS_TOKEN;


  private HttpClient httpClient;

  static {
    ORS_TOKEN = System.getenv("ORS_TOKEN");
  }

  public OpenRouteAdapter() {
    httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
  }

  public OpenRouteAdapter(HttpClient httpClient) {
    this.httpClient = httpClient;
  }

  /**
   * Limiting for simplicity the size to 1 so we always get the most relevant city
   * 
   * @param cityName
   * @return
   */
  private String buildSearchURI(String cityName) {
    return String.format("%s/geocode/search?api_key=%s&text=%s&layers=%s&size=%s",
        OPENROUTE_URL.getValue(), ORS_TOKEN, cityName.replace(" ", "%20"), "locality", "1");
  }

  private void isJSONArrayEmpty(JSONArray jsonArray, String name) throws JSONParseException {
    if (jsonArray.isEmpty()) {
      logger.error("JSON ARRAY {} is empty. Maybe check your input parameters ?. ", name);
      throw new JSONParseException();
    }
  }

  private Coordinate parseCoordinate(String jsonResponse) throws JSONParseException {
    JSONObject jsonObjectResponse = new JSONObject(jsonResponse);


    if (!jsonObjectResponse.has(FEATURES.getValue())) {
      logger.error(JSON_EXCEPTION, FEATURES.getValue());
      throw new JSONParseException();
    }

    JSONArray featureArray = jsonObjectResponse.getJSONArray(FEATURES.getValue());
    isJSONArrayEmpty(featureArray, FEATURES.getValue());

    if (!featureArray.getJSONObject(0).has(GEOMETRY.getValue())) {
      logger.error(JSON_EXCEPTION, GEOMETRY.getValue());
      throw new JSONParseException();
    }

    JSONArray coordinate = featureArray.getJSONObject(0).getJSONObject(GEOMETRY.getValue())
        .getJSONArray(COORDINATES.getValue());

    isJSONArrayEmpty(coordinate, COORDINATES.getValue());

    return new Coordinate(coordinate.getBigDecimal(0), coordinate.getBigDecimal(1));
  }

  private BigDecimal parseDistance(String jsonResponse) throws JSONParseException {
    JSONObject jsonObjectResponse = new JSONObject(jsonResponse);

    if (!jsonObjectResponse.has(DISTANCES.getValue())) {
      logger.error(JSON_EXCEPTION, DISTANCES.getValue());
      throw new JSONParseException();
    } ;

    JSONArray distances = jsonObjectResponse.getJSONArray(DISTANCES.getValue());

    isJSONArrayEmpty(distances, DISTANCES.getValue());
    isJSONArrayEmpty(distances.getJSONArray(0), DISTANCES.getValue());

    if (distances.getJSONArray(0).isNull(0)) {
      logger.error(JSON_EXCEPTION, DISTANCES.getValue());
      throw new JSONParseException();
    }

    return distances.getJSONArray(0).getBigDecimal(0);
  }

  /**
   * returns the coordinate of a given city
   * 
   * @param cityName
   * @return Coordinate
   * @throws OpenRouteConnectionException
   * @throws JSONParseException
   */
  public Coordinate getCityCoordinates(String cityName)
      throws JSONParseException, OpenRouteConnectionException, InvalidValueException {
    logger.debug("entering getCityCoordinates with city name {}", cityName);
    try {
      if (cityName == null || cityName.isBlank()) {
        logger.error("City name cannot be null or empty");
        throw new InvalidValueException();
      }
      HttpRequest request =
          HttpRequest.newBuilder().GET().uri(URI.create(buildSearchURI(cityName))).build();

      HttpResponse<String> response =
          httpClient.send(request, HttpResponse.BodyHandlers.ofString());

      return parseCoordinate(response.body());
    } catch (IOException | InterruptedException e) {
      logger.error("Couldn't connect to OpenRoute connection Failure", e);
      throw new OpenRouteConnectionException();
    }
  }

  /**
   * Returns the distance between two coordinates for a driving car
   * 
   * @param source
   * @param destination
   * @return distance
   * @throws JSONParseException
   * @throws OpenRouteConnectionException
   */
  public BigDecimal getDistanceBetweenCoordinates(Coordinate source, Coordinate destination)
      throws JSONParseException, OpenRouteConnectionException, InvalidValueException {

    try {
      if (source == null || destination == null) {
        logger.error("Coorindates cannot be null");
        throw new InvalidValueException();
      }

      logger.debug(
          "entering getDistanceBetweenCoordinates with source : {} , {} and destination : {} , {}",
          source.getLatitude(), source.getLongitude(), destination.getLatitude(),
          destination.getLongitude());

      String jsonRequest = new DistanceRequestBuilder().source(source).destination(destination)
          .units("km").metrics("distance").buildJSONString();

      HttpRequest httpRequest = HttpRequest.newBuilder()
          .uri(URI.create("https://api.openrouteservice.org/v2/matrix/driving-car"))
          .POST(HttpRequest.BodyPublishers.ofString(jsonRequest))
          .header("Content-Type", "application/json").header("Authorization", ORS_TOKEN).build();

      HttpResponse<String> response =
          httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

      if (response.statusCode() != 200) {
        logger.error("Return Status from OpenRoute is {}", response.statusCode());
        throw new OpenRouteConnectionException();
      }

      if (response.body() == null || response.body().isBlank()) {
        logger.error(
            "Response Body is empty or null from OpenRoute, please check the input parameters");
        throw new InvalidValueException();
      }

      return parseDistance(response.body());
    } catch (IOException | InterruptedException e) {
      logger.error("Couldn't connect to OpenRoute connection Failure", e);
      throw new OpenRouteConnectionException();
    }
  }
}
