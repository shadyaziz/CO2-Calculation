package com.sap.challenge.openroute;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.http.HttpClient;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import com.sap.challenge.exception.InvalidValueException;
import com.sap.challenge.openroute.adapter.OpenRouteAdapter;
import com.sap.challenge.openroute.exception.JSONParseException;
import com.sap.challenge.openroute.exception.OpenRouteConnectionException;
import com.sap.challenge.openroute.model.Coordinate;

/**
 * 
 * OpenRoute adapter test class
 *
 */
public class OpenRouteAdapterTest {
  OpenRouteAdapter adapter;

  @Before
  public void initTest() {
    adapter = new OpenRouteAdapter();
    assertTrue(System.getenv("ORS_TOKEN") != null && !System.getenv("ORS_TOKEN").isBlank());
  }

  @Test
  public void getCityCoordinatesPositive()
      throws JSONParseException, OpenRouteConnectionException, InvalidValueException {
    Coordinate coordinate = adapter.getCityCoordinates("berlin");

    assertNotNull(coordinate);

    BigDecimal latitude = coordinate.getLatitude().setScale(3, RoundingMode.HALF_EVEN);
    BigDecimal longitude = coordinate.getLongitude().setScale(3, RoundingMode.HALF_EVEN);
    assertTrue(latitude.compareTo(new BigDecimal(13.407).setScale(3, RoundingMode.HALF_EVEN)) == 0);
    assertTrue(
        longitude.compareTo(new BigDecimal(52.520).setScale(3, RoundingMode.HALF_EVEN)) == 0);
  }

  @Test(expected = JSONParseException.class)
  public void getCityCoordinatesInvalidCityName()
      throws JSONParseException, OpenRouteConnectionException, InvalidValueException {
    adapter.getCityCoordinates("asd1234");
  }

  @Test(expected = InvalidValueException.class)
  public void getCityCoordinatesNullCityName()
      throws JSONParseException, OpenRouteConnectionException, InvalidValueException {
    adapter.getCityCoordinates(null);
  }

  @Test
  public void getDistanceBetweenCoordinatesPositive()
      throws JSONParseException, OpenRouteConnectionException, InvalidValueException {
    Coordinate source =
        new Coordinate(new BigDecimal(10.0070460000000007738663043710403144359588623046875),
            new BigDecimal(53.57615799999999950387064018286764621734619140625));
    Coordinate destination =
        new Coordinate(new BigDecimal(13.4073200000000003484501576167531311511993408203125),
            new BigDecimal(52.5204499999999967485564411617815494537353515625));

    BigDecimal distance = adapter.getDistanceBetweenCoordinates(source, destination);

    assertTrue(distance.compareTo(new BigDecimal(288)) == 0);
  }

  @Test(expected = JSONParseException.class)
  public void getDistanceBetweenCoordinatesInvalidValues()
      throws JSONParseException, OpenRouteConnectionException, InvalidValueException {

    Coordinate source = new Coordinate(new BigDecimal(-3), new BigDecimal(-3));
    Coordinate destination = new Coordinate(new BigDecimal(-6), new BigDecimal(10));

    adapter.getDistanceBetweenCoordinates(source, destination);
  }

  @Test(expected = InvalidValueException.class)
  public void getDistanceBetweenCoordinatesNullValues()
      throws JSONParseException, OpenRouteConnectionException, InvalidValueException {
    Coordinate source = new Coordinate(null, null);
    Coordinate destination = new Coordinate(null, null);

    adapter.getDistanceBetweenCoordinates(source, destination);
  }

  @Test(expected = InvalidValueException.class)
  public void getDistanceBetweenCoordinatesNullValues_2()
      throws JSONParseException, OpenRouteConnectionException, InvalidValueException {

    adapter.getDistanceBetweenCoordinates(null, null);
  }

  @Test(expected = OpenRouteConnectionException.class)
  public void serviceNotAvailable() throws JSONParseException, OpenRouteConnectionException,
      InvalidValueException, IOException, InterruptedException {

    HttpClient httpClient = Mockito.mock(HttpClient.class);

    adapter = new OpenRouteAdapter(httpClient);

    Mockito.when(httpClient.send(any(), any())).thenThrow(IOException.class);

    adapter.getCityCoordinates("asd");
  }

}
