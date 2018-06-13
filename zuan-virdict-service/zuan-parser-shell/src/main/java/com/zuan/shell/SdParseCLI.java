/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.shell;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.jline.utils.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import com.zuan.parser.imp.exception.ParserException;
import com.zuan.parser.utils.ParserUtils;
import com.zuan.shell.common.SdObject;
import com.zuan.shell.configuration.ParserConfigurationProperties;
import com.zuan.shell.provider.FileInputProvider;
import com.zuan.shell.service.SdSignalService;
import com.zuan.shell.utils.BinaryUtils;

/**
 * The Class SdParseCLI.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">quanmd.nv</a>
 */
@ShellComponent
public class SdParseCLI {

  /** The Constant LOGGER. */
  private static final Logger LOGGER = LoggerFactory.getLogger(SdParseCLI.class);

  /** The ctx. */
  @Autowired
  private ConfigurableApplicationContext ctx;

  /** The properties. */
  @Autowired
  private ParserConfigurationProperties properties;

  /** The sd signal service. */
  @Autowired
  private SdSignalService sdSignalService;

  /**
   * Instantiates a new sd parse cli.
   */
  public SdParseCLI() {
    // do nothing
  }

  /**
   * Sd value.
   *
   * @param path
   *          the path
   * @param trainProject
   *          the train project
   * @param signalCodes
   *          the signal codes
   * @return the sets the
   */
  @ShellMethod(value = "Get values from list signal code.")
  public void signalValue(
      @ShellOption(arity = 1, defaultValue = "config/input/signal-value", valueProvider = FileInputProvider.class) final File path,
      @ShellOption(arity = 1) final String trainProject,
      @ShellOption(defaultValue = ShellOption.NULL) final String signalCodes) {
    String[] listSignalCode = StringUtils.split(signalCodes, ',');
    final Set<String> listSignals = new HashSet<>();
    if (listSignalCode != null && listSignalCode.length > 0) {
      for (final String signalCode : listSignalCode) {
        listSignals.add(signalCode);
      }
    }
    Set<SdObject> sdObject = new HashSet<>();
    try {
      if (path.isDirectory()) {
        final List<File> files =
            Files.list(path.toPath()).map(Path::toFile).collect(Collectors.toList());
        for (final File sdFile : files) {
          final byte[] byteArr = BinaryUtils.convertFileToByteArr(sdFile);
          final byte[] sdBinary = ParserUtils.getSdBinary(byteArr);
//          sdObject.addAll(ParserUtils.getSdObjectValue(sdBinary, listSignals, trainProject,
//              sdSignalService.getSignalConfiguration(trainProject),
//              properties.getSdNumberPosition(), properties.getSdLengthMapper()));
        }
      } else {
        final byte[] byteArr = BinaryUtils.convertFileToByteArr(path);
        final byte[] sdBinary = ParserUtils.getSdBinary(byteArr);
//        sdObject.addAll(ParserUtils.getSdObjectValue(sdBinary, listSignals, trainProject,
//            sdSignalService.getSignalConfiguration(trainProject),
//            properties.getSdNumberPosition(), properties.getSdLengthMapper()));
      }
      Log.info(BinaryUtils.printSdObjectTable(sdObject).toString());
    } catch (IOException | ParserException e) {
      LOGGER.error("", e);
    }
  }

  /**
   * Exit program.
   */
  @ShellMethod(group = "Exit program.", key = {"q" }, value = "Exit the program.")
  public void exitProgram() {
    int exitCode = SpringApplication.exit(ctx, () -> 0);
    System.exit(exitCode);
  }

}
