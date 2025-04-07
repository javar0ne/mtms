package server.model;

public class SendPackageResponse {

    private String trackingNumber;

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    @Override
    public String toString() {
        return "SendPackageResponse [trackingNumber=" + trackingNumber + "]";
    }
}
