package com.sap.challenge.openroute.model;

import java.util.Arrays;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * 
 * Builder class for DistanceRequest
 *
 */
public class DistanceRequestBuilder {
  /**
   * source and destination coordinations
   */
  private Coordinate source;
  private Coordinate destination;

  /**
   * Always has distance value
   */
  private String metrics;
  /**
   * Always has km value
   */
  private String units;

  public DistanceRequestBuilder source(Coordinate source) {
    this.source = source;
    return this;
  }

  public DistanceRequestBuilder destination(Coordinate destination) {
    this.destination = destination;
    return this;
  }

  public DistanceRequestBuilder metrics(String metrics) {
    this.metrics = metrics;
    return this;
  }

  public DistanceRequestBuilder units(String units) {
    this.units = units;
    return this;
  }

  public String buildJSONString() {
    JSONObject requestJSONObject = new JSONObject();
    JSONArray locations = new JSONArray();

    locations.put(Arrays.asList(source.getLatitude(), source.getLongitude()));
    locations.put(Arrays.asList(destination.getLatitude(), destination.getLongitude()));

    requestJSONObject.put("locations", locations);
    requestJSONObject.put("units", units);
    requestJSONObject.put("metrics", Arrays.asList(metrics));
    // setting source city as position number 0
    requestJSONObject.put("sources", Arrays.asList(0));
    // setting destination city as position number 1
    requestJSONObject.put("destinations", Arrays.asList(1));

    return requestJSONObject.toString();

  }

}
