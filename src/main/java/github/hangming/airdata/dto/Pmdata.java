package github.hangming.airdata.dto;


public class Pmdata {
	
	String pm10; // "-"
	String pm25;
	String time;
	
	int station;
	String stationName;
	String region;
	
	double lat;
	double lng;
	
	int avgPm10;
	int avgPm25;
	int countPm10;
	int countPm25;
	
	


	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}
	public String getPm10() {
		return pm10;
	}
	public boolean hasValidPm10 () {
		return pm10.indexOf('-') < 0 ; 
	}
	
	public double getPm10Value() {
		try {
			double val = Double.parseDouble(pm10);
			return val;
		} catch ( NumberFormatException nfe ) {
			return 0;
		}
	}
	
	public double getPm25Value() {
		try {
			double val = Double.parseDouble(pm25);
			return val;
		} catch ( NumberFormatException nfe ) {
			return 0;
		}
	}
	
	public void setPm10(String pm10) {
		this.pm10 = pm10;
	}
	public String getPm25() {
		return pm25;
	}
	public void setPm25(String pm25) {
		this.pm25 = pm25;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getStation() {
		return station;
	}
	public void setStation(int station) {
		this.station = station;
	}
	public String getStationName() {
		return stationName;
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
	
	
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	
	
	public int getAvgPm10() {
		return avgPm10;
	}
	public void setAvgPm10(int avgPm10) {
		this.avgPm10 = avgPm10;
	}
	public int getAvgPm25() {
		return avgPm25;
	}
	public void setAvgPm25(int avgPm25) {
		this.avgPm25 = avgPm25;
	}
	public int getCountPm10() {
		return countPm10;
	}
	public void setCountPm10(int countPm10) {
		this.countPm10 = countPm10;
	}
	public int getCountPm25() {
		return countPm25;
	}
	public void setCountPm25(int countPm25) {
		this.countPm25 = countPm25;
	}
	
	
	
	@Override
	public String toString() {
		return "Pmdata [pm10=" + pm10 + ", pm25=" + pm25 + ", time=" + time + "stationName"+ stationName +"region"+ region +"]"  ;

	}
}


