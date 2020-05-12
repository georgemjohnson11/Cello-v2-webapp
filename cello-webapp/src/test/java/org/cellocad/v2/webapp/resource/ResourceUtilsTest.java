/*
 * Copyright (C) 2020 Boston University (BU)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package org.cellocad.v2.webapp.resource;

import java.io.File;
import java.io.IOException;
import org.junit.Test;

/**
 * Tests for {@link ResourceUtils}.
 *
 * @author Timothy Jones
 * @date 2020-05-12
 */
public class ResourceUtilsTest {

  /**
   * Test {@link ResourceUtils#initResources()}. The test only checks that certain files and
   * directories are created.
   *
   * @throws IOException Unable to initialize resources.
   */
  @Test
  public void initResources_None_SouldCreateExpectedFiles() throws IOException {
    ResourceUtils.initResources();
    File settingsDir = new File(ResourceUtils.getSettingsResourcesDirectory());
    File userConstraintsMeta = new File(ResourceUtils.getUserConstraintsFileMetaDataFile());
    File inputMeta = new File(ResourceUtils.getInputSensorFileMetaDataFile());
    File outputMeta = new File(ResourceUtils.getOutputDeviceFileMetaDataFile());
    assert (settingsDir.exists()
        && userConstraintsMeta.exists()
        && inputMeta.exists()
        && outputMeta.exists());
  }
}
