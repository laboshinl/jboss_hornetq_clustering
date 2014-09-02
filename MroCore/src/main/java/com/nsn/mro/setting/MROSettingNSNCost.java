package com.nsn.mro.setting;

import java.util.HashMap;
import java.util.Map;

/**
 *  MRO Setting for NSN Cost
 * @author acp
 *
 */
public class MROSettingNSNCost extends MROSettingCommon {

	// Complex Cost Function specific settings

	MROSettingItf.OptimizationProfile optimizaitonProfile;

	Map<String, Double> nsnsetting = new HashMap<String,Double>();

	public MROSettingNSNCost() {

		// KPI Triggering Thresholds - Advanced Setting
		nsnsetting.put("lateHOTrigThr", 0.0);
		nsnsetting.put("lateHOTrigThr", 0.0);
		nsnsetting.put("wrongCellReestHOTrigThr", 0.0);
		nsnsetting.put("earlyHOTrigThr", 0.0);
		nsnsetting.put("wrongCellTgtHOTrigThr", 0.0);
		nsnsetting.put("pingpongHOTrigThr", 0.0);

		nsnsetting.put("compromiseFactor", 0.0); // Advanced Setting

		// Saturation Ratios - Advanced Setting
		nsnsetting.put("targetKPISaturationRatio", 0.0);
		nsnsetting.put("nonTriggeringKPISaturationRatio", 0.0);

		// KPI sensitivity factors - Advanced Setting
		nsnsetting.put("lateHOSensitivityFactor", 0.0);
		nsnsetting.put("wrongCellReestHOSensitivityFactor", 0.0);
		nsnsetting.put("earlyHOSensitivityFactor", 0.0);
		nsnsetting.put("wrongCellTgtHOSensitivityFactor", 0.0);
		nsnsetting.put("pingpongHOSensitivityFactor", 0.0);

		nsnsetting.put("maximumStepSize", 0.0);

	}


	@Override
	public Map<String, Double> getMroSetting() {
		return nsnsetting;
	}

}
