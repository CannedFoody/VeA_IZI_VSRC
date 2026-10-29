package eu.virac.vea_izi_vsrc.model.Enums;

public enum KPIStatus {
    Approved("LOW"),
    Denied("LOW"),
    InProgress("HIGH"),
    Cancelled("LOW"),
    NeedChanges("HIGH");

    private final String hiearchy;

    KPIStatus(String hiearchy) {
        this.hiearchy = hiearchy;
    }

    public String getHiearchy() {
        return hiearchy;
    }
}