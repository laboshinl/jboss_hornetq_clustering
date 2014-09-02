package com.nsn.mro.setting;

import java.util.HashMap;
import java.util.Map;

/**
 * Common MRO Settings
 * @author acp
 *
 */
public abstract class MROSettingCommon implements MROSettingItf {

	boolean isTTEnabled;
	Map<String, Double> commonSetting = new HashMap<String, Double>();

	OptimizationProfile optimizationProfile;
	FreezeStratergy freezeStartergy;

	// Oscillation Detection Settings
	// TODO ?

	MROSettingCommon() {
		// Pre filtering threshold
		commonSetting.put("handoverFailureCountThr", 0.0);
		commonSetting.put("handoverFailureRateThr", 0.0);
		commonSetting.put("pingpongCountThr", 0.0);
		commonSetting.put("pingpongHandoverRateThr", 0.0);

		// CIO Limits

		commonSetting.put("cio_intraFreqMin", 0.0);
		commonSetting.put("cio_intraFreqMax", 0.0);
		commonSetting.put("cio_interFreqMax", 0.0);
		commonSetting.put("cio_interFreqMin", 0.0);

		// TTT limits

		commonSetting.put("ttt_intraFreqMin", 0.0);
		commonSetting.put("ttt_intraFreqMax", 0.0);
		commonSetting.put("ttt_interFreqMax", 0.0);
		commonSetting.put("ttt_interFreqMin", 0.0);

	}

	@Override
	public OptimizationProfile getOptimizationProfile() {
		return this.optimizationProfile;
	}

	@Override
	public void setOptimizationProfile(OptimizationProfile opProfile) {
		this.optimizationProfile = opProfile;

	}

	@Override
	public FreezeStratergy getFreezeStratergy() {
		return this.freezeStartergy;
	}

	@Override
	public void setFreezeStratergy(FreezeStratergy freezeStartergy) {
		this.freezeStartergy = freezeStartergy;

	}

	@Override
	public boolean isTTTEnabled() {

		return isTTEnabled;
	}

	@Override
	public void enableTTT(boolean enable) {
		this.isTTEnabled = enable;

	}


	@Override
	public double getMroSetting(String name) {

		validate(name);
		if(commonSetting.get(name)==null){
			return getMroSetting().get(name);
		}
		return commonSetting.get(name);
	}

	@Override
	public void setMroSetting(String name, double value) {

		validate(name);

		if(getMroSetting().get(name) != null){ 
			getMroSetting().remove(name);
			getMroSetting().put(name, value);
		}

		if(commonSetting.get(name) != null){ 
			commonSetting.remove(name);
			commonSetting.put(name, value);
		}
	}

	/**
	 * @param name
	 */
	private void validate(String name) {
		if( (getMroSetting().get(name) == null) &&(commonSetting.get(name)==null) ) {
			throw new IllegalArgumentException(name + " is not a Specific Cost or Common Setting");
		}
	}


}
