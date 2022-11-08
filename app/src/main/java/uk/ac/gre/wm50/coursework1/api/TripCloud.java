package uk.ac.gre.wm50.coursework1.api;

public class TripCloud {

    String names;
    String uploadResponseCode;
    String message;
    int number;
    String userid;

    public TripCloud(String names, String uploadResponseCode, String message, int number, String userid) {
        this.names = names;
        this.uploadResponseCode = uploadResponseCode;
        this.message = message;
        this.number = number;
        this.userid = userid;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getUploadResponseCode() {
        return uploadResponseCode;
    }

    public void setUploadResponseCode(String uploadResponseCode) {
        this.uploadResponseCode = uploadResponseCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}

