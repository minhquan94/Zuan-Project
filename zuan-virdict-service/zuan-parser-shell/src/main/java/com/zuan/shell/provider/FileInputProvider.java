/*
 * Copyright (c) 2018 Zuan_Wiko
 */
package com.zuan.shell.provider;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.shell.CompletionContext;
import org.springframework.shell.CompletionProposal;
import org.springframework.shell.standard.ValueProvider;

import com.zuan.shell.common.FileInput;

import jline.console.completer.FileNameCompleter;

/**
 * The Class FileValueProvider.
 *
 * @author <a href="mailto:developer@hitachiconsulting.com">quanmd.nv</a>
 */
public class FileInputProvider extends FileNameCompleter implements ValueProvider {

  /**
   * {@inheritDoc}
   * 
   * @see org.springframework.shell.standard.ValueProvider#supports(org.springframework.core.MethodParameter,
   *      org.springframework.shell.CompletionContext)
   */
  @Override
  public boolean supports(MethodParameter parameter, CompletionContext completionContext) {
    return parameter.getParameterType().isAssignableFrom(FileInput.class);
  }

  /**
   * {@inheritDoc}
   * 
   * @see org.springframework.shell.standard.ValueProvider#complete(org.springframework.core.MethodParameter,
   *      org.springframework.shell.CompletionContext, java.lang.String[])
   */
  @Override
  public List<CompletionProposal> complete(MethodParameter parameter,
      CompletionContext completionContext, String[] hints) {
    String input = completionContext.currentWordUpToCursor();
    final String prefixName = FilenameUtils.getName(input);
    try {
      return getFiles(input).stream()
          .filter(file -> StringUtils
              .startsWithIgnoreCase(FilenameUtils.getName(file.getName()), prefixName))
          .map(p -> new CompletionProposal(p.toString())).collect(Collectors.toList());
    } catch (IOException e) {
      throw new UncheckedIOException(e);
    }
  }

  /**
   * Gets the files.
   *
   * @param input
   *          the input
   * @return the files
   * @throws IOException
   *           Signals that an I/O exception has occurred.
   */
  private List<File> getFiles(final String input) throws IOException {
    String translated = FilenameUtils.separatorsToUnix(input);
    final File homeDir = getUserHome();
    // Special character: ~ maps to the user's home directory
    if (translated.startsWith("~" + separator())) {
      translated = homeDir.getPath() + translated.substring(1);
    } else if (translated.startsWith("~")) {
      translated = homeDir.getParentFile().getAbsolutePath();
    } else if (!(new File(translated).isAbsolute())) {
      String cwd = getUserDir().getAbsolutePath();
      translated = cwd + separator() + translated;
    }
    final File file = new File(translated);
    final File dir;

    if (translated.endsWith(separator())) {
      dir = file.getCanonicalFile();
    } else {
      dir = file.getParentFile().getCanonicalFile();
    }

    return dir == null ? new ArrayList<>()
        : Files.list(dir.toPath()).map(Path::toFile).collect(Collectors.toList());
  }
}
