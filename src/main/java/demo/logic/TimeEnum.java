package demo.logic;

public enum TimeEnum {
	DAY("lastDay", 1), WEEK("lastWeek", 7), MONTH("lastMonth", 30 );

    public final String filterType;
    public final long daysValue;

    private TimeEnum(String filterType, long daysValue) {
    	this.filterType = filterType;
    	this.daysValue = daysValue;
    }
    

    public static TimeEnum fromString(String filterType) {
    	for (TimeEnum te : TimeEnum.values()) {
    		if (te.filterType.equalsIgnoreCase(filterType)) {
    			return te;
    		}
    	}
    	return null;
    }
}
