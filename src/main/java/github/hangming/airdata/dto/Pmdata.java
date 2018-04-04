package github.hangming.airdata.dto;

public class Pmdata {
	
	String pm10; // "-"
	String pm25;
	String time;
	
	int station;
	String stationName;
	
	
	
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
	@Override
	public String toString() {
		return "Pmdata [pm10=" + pm10 + ", pm25=" + pm25 + ", time=" + time + "]" + stationName ;
	}

	
}
