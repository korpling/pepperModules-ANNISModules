/**
 * Copyright 2015 Humboldt-Universität zu Berlin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 */
package org.corpus_tools.peppermodules.annis;

public class SegmentationInfo {

  Long annisId = 0l;
  String segmentationName = "";
  String span = "";

  public SegmentationInfo(Long annisId, String segmentationName, String span) {
    this.annisId = annisId;
    this.segmentationName = segmentationName;
    this.span = span;
  }

  Long getANNISId() {
    return this.annisId;
  }

  String getSegmentationName() {
    return this.segmentationName;
  }

  String getSpan() {
    return this.span;
  }

}
