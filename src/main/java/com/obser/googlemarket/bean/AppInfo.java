package com.obser.googlemarket.bean;

public class AppInfo {
    	 
    private long id;
	private String name;
	private String packageName;
	private String iconUrl;
	private double stars;
	private long size;
	private String downloadUrl;
	private String des;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getIconUrl() {
		return iconUrl;
	}
	public void setIconUrl(String iconUrl) {
		this.iconUrl = iconUrl;
	}
	public double getStars() {
		return stars;
	}
	public void setStars(double stars) {
		this.stars = stars;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public String getDownloadUrl() {
		return downloadUrl;
	}
	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public AppInfo() {
		super();
	}
	public AppInfo(long id, String name, String packageName, String iconUrl,
			double stars, long size, String downloadUrl, String des) {
		super();
		this.id = id;
		this.name = name;
		this.packageName = packageName;
		this.iconUrl = iconUrl;
		this.stars = stars;
		this.size = size;
		this.downloadUrl = downloadUrl;
		this.des = des;
	}
	@Override
	public String toString() {
		return "AppInfo [id=" + id + ", name=" + name + ", packageName="
				+ packageName + ", iconUrl=" + iconUrl + ", stars=" + stars
				+ ", size=" + size + ", downloadUrl=" + downloadUrl + ", des="
				+ des + "]";
	}
	
	
}
