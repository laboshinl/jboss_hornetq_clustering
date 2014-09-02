package com.nsn.mro.setting;

import java.util.HashMap;
import java.util.Map;

/**
 * MRO Settings for KDDI related Cost Function
 * @author acp
 *
 */
public class MROSettingKDDICost  extends MROSettingCommon{

	// Simple Cost function specific settings

	Map<String,Double> kddisetting = new HashMap<String,Double>();

	public MROSettingKDDICost(){

		kddisetting.put("mroWeightTE", 0.0);
		kddisetting.put("mroWeightTL", 0.0);
		kddisetting.put("mroWeightWR", 0.0);
		kddisetting.put("mroWeightWT", 0.0);
		kddisetting.put("mroWeightPP", 0.0);
	}

	@Override
	public Map<String, Double> getMroSetting() {
		return kddisetting;
	}


}