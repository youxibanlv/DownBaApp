package com.strike.downba_app.http.entity;

public class Version {
    private int id;
    private int version_code;
    private String version_name;
    private int channel_id;
    private long update_time;
    private String version_info;
    private String url;
    private String dateTime;

    public Version(int id, int version_code, String url, String version_name, int channel_id, long update_time,
                   String version_info) {
        super();
        this.id = id;
        this.url = url;
        this.version_code = version_code;
        this.version_name = version_name;
        this.channel_id = channel_id;
        this.update_time = update_time;
        this.version_info = version_info;
    }

    public Version() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVersion_code() {
        return version_code;
    }

    public void setVersion_code(int version_code) {
        this.version_code = version_code;
    }

    public String getVersion_name() {
        return version_name;
    }

    public void setVersion_name(String version_name) {
        this.version_name = version_name;
    }

    public int getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(int channel_id) {
        this.channel_id = channel_id;
    }

    public long getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(long update_time) {
        this.update_time = update_time;
    }

    public String getVersion_info() {
        return version_info;
    }

    public void setVersion_info(String version_info) {
        this.version_info = version_info;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}
