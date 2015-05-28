package de.hu_berlin.german.korpling.saltnpepper.pepperModules.annis;

public class SegmentationInfo {

  Long relANNISId = 0l;
  String segmentationName = "";
  String span = "";

  public SegmentationInfo(Long relAnnisId, String segmentationName, String span) {
    this.relANNISId = relAnnisId;
    this.segmentationName = segmentationName;
    this.span = span;
  }

  Long getRelANNISId() {
    return this.relANNISId;
  }

  String getSegmentationName() {
    return this.segmentationName;
  }

  String getSpan() {
    return this.span;
  }

}
