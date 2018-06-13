/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.shell.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.shell.table.BeanListTableModel;
import org.springframework.shell.table.BorderStyle;
import org.springframework.shell.table.Table;
import org.springframework.shell.table.TableBuilder;

import com.zuan.shell.common.SdObject;
import com.zuan.shell.common.SdObjectTable;

/**
 * The Class BinaryUtils.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">quanmd.nv</a>
 */
public final class BinaryUtils {

  /**
   * Instantiates a new binary utils.
   */
  private BinaryUtils() {
    // do nothing
  }

  /**
   * Convert file to byte arr.
   *
   * @param input
   *          the input
   * @return the byte[]
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
  public static byte[] convertFileToByteArr(final File input) throws IOException {
    try (final InputStream inputStream = new FileInputStream(input)) {
      return IOUtils.toByteArray(inputStream);
    }
  }

  /**
   * Gets the byte length.
   *
   * @param bitLength
   *          the bit length
   * @return the byte length
   */
  public static int getByteLength(int bitLength) {
    if (bitLength > 8) {
      return bitLength / 8 + 1;
    }
    return 1;
  }

  /**
   * Convert file to byte arr.
   *
   * @param path
   *          the path
   * @return the byte[]
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
  public static byte[] convertFileToByteArr(final String path) throws IOException {
    return convertFileToByteArr(Paths.get(path).toFile());
  }

  /**
   * Prints the sd object table.
   *
   * @param objects
   *          the objects
   * @return the string
   */
  public static Table printSdObjectTable(final Set<SdObject> objects) {
    LinkedHashMap<String, Object> header = new LinkedHashMap<>();
    header.put("sdNumber", "SD Number");
    header.put("signalCode", "Signal Code");
    header.put("description", "Signal Name");
    header.put("byteOffset", "Byte Offset");
    header.put("bitOffset", "Bit Offset");
    header.put("bitLength", "Bit Length");
    header.put("value", "Value");
    final List<SdObject> sortedList = new ArrayList<>(objects);
    Collections.sort(sortedList);
    return new TableBuilder(new BeanListTableModel<SdObject>(sortedList, header))
        .addFullBorder(BorderStyle.oldschool).build();
  }

  /**
   * Convert to table.
   *
   * @param objects
   *          the objects
   * @return the sets the
   */
  public static Set<SdObjectTable> convertToTable(final Set<SdObject> objects) {
    final Set<SdObjectTable> objectTables = new HashSet<>();
    int count = 1;
    for (final SdObject sdObject : objects) {
      final SdObjectTable objectTable = new SdObjectTable();
      objectTable.setId(count);
      objectTable.setSignalCode(AnsiOutput.toString(AnsiColor.BRIGHT_YELLOW,
          sdObject.getSignalCode(), AnsiColor.DEFAULT));
      objectTable.setDescription(
          AnsiOutput.toString(AnsiColor.YELLOW, sdObject.getDescription(), AnsiColor.DEFAULT));
      objectTable.setBitLength(
          AnsiOutput.toString(AnsiColor.YELLOW, sdObject.getBitLength(), AnsiColor.DEFAULT));
      objectTable.setBitOffset(
          AnsiOutput.toString(AnsiColor.YELLOW, sdObject.getBitOffset(), AnsiColor.DEFAULT));
      objectTable.setByteOffset(
          AnsiOutput.toString(AnsiColor.YELLOW, sdObject.getByteOffset(), AnsiColor.DEFAULT));
      objectTable.setValue(AnsiOutput.toString(AnsiColor.BRIGHT_YELLOW, sdObject.getValue(),
          AnsiColor.DEFAULT));
      objectTables.add(objectTable);
    }
    return objectTables;
  }
}
