package de.hu_berlin.german.korpling.saltnpepper.pepperModules.annis;

public class SegmentationInfo {

  Long annisId = 0l;
  String segmentationName = "";
  String span = "";

  public SegmentationInfo(Long relAnnisId, String segmentationName, String span) {
    this.annisId = relAnnisId;
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
