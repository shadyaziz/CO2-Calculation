package com.sap.challenge.main;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.sap.challenge.constants.EngineType;
import com.sap.challenge.constants.Size;
import com.sap.challenge.constants.TransportationType;
import com.sap.challenge.exception.InvalidValueException;

/**
 * 
 * a parser based on Apache CLI to read user values
 *
 */
public abstract class MainArgsParser {
  private static final Logger logger = LogManager.getLogger(MainArgsParser.class);

  private static final String START_OPTION = "start";
  private static final String END_OPTION = "end";
  private static final String TRANSPORTATION_METHOD_OPTION = "transportation-method";
  private static Options options;

  static {
    options = new Options();

    options.addOption(Option.builder().argName(START_OPTION).longOpt(START_OPTION).hasArg(true)
        .required(true).build());

    options.addOption(Option.builder().argName(END_OPTION).longOpt(END_OPTION).hasArg(true)
        .required(true).build());

    options.addOption(Option.builder().argName(TRANSPORTATION_METHOD_OPTION)
        .longOpt(TRANSPORTATION_METHOD_OPTION).hasArg(true).required(true).build());
  }

  /**
   * Parse arguments given by the user
   * 
   * @param args
   * @return
   * @throws ParseException
   * @throws InvalidValueException
   */
  public static MainArgs parseArgs(String[] args) throws ParseException, InvalidValueException {
    CommandLineParser parser = new DefaultParser();
    CommandLine cmd = parser.parse(options, args);

    MainArgs mainArgs = new MainArgs();

    mainArgs.setSourceCity(cmd.getOptionValue(START_OPTION));
    mainArgs.setDestinationCity(cmd.getOptionValue(END_OPTION));

    String[] transportationArgs = cmd.getOptionValue(TRANSPORTATION_METHOD_OPTION).split("-");
    try {
      if (transportationArgs.length > 1 && transportationArgs.length == 3) {
        mainArgs.setSize(Size.valueOf(transportationArgs[0].toUpperCase()));
        mainArgs.setEngine(EngineType.valueOf(transportationArgs[1].toUpperCase()));
        mainArgs.setTransportation(TransportationType.valueOf(transportationArgs[2].toUpperCase()));
      } else if (transportationArgs.length == 1) {
        mainArgs.setTransportation(TransportationType.valueOf(transportationArgs[0]));
      } else {
        logger.error("Transportation Type has invalid value {}",
            cmd.getOptionValue(TRANSPORTATION_METHOD_OPTION));
        throw new InvalidValueException();
      }
    } catch (IllegalArgumentException e) {
      logger.error("Transportation Type has invalid value {}",
          cmd.getOptionValue(TRANSPORTATION_METHOD_OPTION));
      throw new InvalidValueException();
    }
    return mainArgs;
  }
}
