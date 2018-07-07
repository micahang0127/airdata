package github.hangming.airdata.dto;

public class FavorStationDto {
	
	int user;					// Mapper 의 resultMap 에 정의 된 property명과 같아야 한다. 
	int station;
	double pm10Limit;
	double pm25Limit;
	String location;
	

	public int getUser() {
		return user;
	}
	public void setUser(int user) {
		this.user = user;
	}
	public int getStation() {
		return station;
	}
	public void setStation(int station) {
		this.station = station;
	}
	public double getPm10Limit() {
		return pm10Limit;
	}
	public void setPm10Limit(double pm10_limit) {
		this.pm10Limit = pm10_limit;
	}
	public double getPm25Limit() {
		return pm25Limit;
	}
	public void setPm25Limit(double pm25_limit) {
		this.pm25Limit = pm25_limit;
	}
	
	
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	
	}
}
