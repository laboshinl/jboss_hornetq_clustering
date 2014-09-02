package com.nsn.mro.setting;

import java.util.Map;

/**
 * An abstarciton for MRO Settings
 * @author acp
 *
 */
public interface MROSettingItf {

	static enum OptimizationProfile {
		Adaptation, Balancing
	};

	static enum FreezeStratergy {
		FullFreeze, PartialFreeze, NoFreeze
	};

	public OptimizationProfile getOptimizationProfile();

	public void setOptimizationProfile(OptimizationProfile opProfile);

	public FreezeStratergy getFreezeStratergy();

	public void setFreezeStratergy(FreezeStratergy freezeStartergy);
	
	public boolean isTTTEnabled();
	
	public void enableTTT(boolean enable);
	
	public double getMroSetting(String name);
	
	public void setMroSetting(String name, double value);
	
	public Map<String,Double> getMroSetting();
	
}
