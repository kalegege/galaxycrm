package com.wasu.ptyw.galaxycrm.web.utile;

public enum RegionEnum {
	
	Quanbu("全部", "*"),
	Gansu("甘肃", "www.utc.gscatv.cn,www.utc.gscatv.com"),
	Yangquan("阳泉", "www.yqdtv.cn"), 
	Yunnan("云南", "yunnan-utc.wasu.cn,kunming-utc.wasu.cn,dali-utc.wasu.cn,yuxi-utc.wasu.cn"), 
	Qinghai("青海", "qinghai-utc.wasu.cn,xining-utc.wasu.cn"), 
	Taiyuan("太原", "taiyuan-utc.wasu.cn"), 
	Kunshan("昆山", "kunshan-utc.wasu.cn"), 
	Chuxiong("楚雄", "chuxiong-utc.wasu.cn"), 
	Nanchang("南昌", "nanchang-utc.wasu.cn"),
	Chongqing("重庆", "h264-chongqing-utc.wasu.cn");
	
	private String region;  
    private String host; 
    
	private RegionEnum(String region, String host) {  
        this.region = region;  
        this.host = host;  
    }

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}  
	
}
