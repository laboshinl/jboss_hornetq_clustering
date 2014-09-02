package com.nokia.mro.setting;


import org.junit.Assert;
import org.junit.Test;

import com.nsn.mro.setting.MROSettingItf;
import com.nsn.mro.setting.MROSettingKDDICost;
import com.nsn.mro.setting.MROSettingNSNCost;

public class TestMROSetting {

	@Test
	public void testMROSettingNSNCostValid(){

		MROSettingItf mroNSNCostSetting = new  MROSettingNSNCost();

		mroNSNCostSetting.setMroSetting("lateHOTrigThr", 11.12);
		Assert.assertTrue(11.12 == mroNSNCostSetting.getMroSetting("lateHOTrigThr"));

		//checking with common setting

		mroNSNCostSetting.setMroSetting("pingpongCountThr", 12.12);
		Assert.assertTrue(12.12 == mroNSNCostSetting.getMroSetting("pingpongCountThr"));

	}

	@Test(expected=IllegalArgumentException.class)
	public void testMROSettingNSNCostinValid(){

		MROSettingItf mroNSNCostSetting = new  MROSettingNSNCost();

		mroNSNCostSetting.setMroSetting("mroWeightTL", 11.12);
		Assert.assertTrue(11.12 == mroNSNCostSetting.getMroSetting("mroWeightTL"));


	}

	@Test(expected=IllegalArgumentException.class)
	public void testMROSettingNSNCostinValid1(){

		MROSettingItf mroNSNCostSetting = new  MROSettingNSNCost();
		//checking with common setting
		mroNSNCostSetting.setMroSetting("pingpongCountThr1", 12.12);
	}


	@Test(expected=IllegalArgumentException.class)
	public void testMROSettingNSNCostinValid2(){

		MROSettingItf mroNSNCostSetting = new  MROSettingNSNCost();
		mroNSNCostSetting.setMroSetting("pingpongCountThr", 12.12);
		mroNSNCostSetting.getMroSetting("pingpongCountThr1");

	}

	@Test
	public void testMROSettingKDDICostValid(){

		MROSettingItf mroNSNCostSetting = new  MROSettingKDDICost();

		mroNSNCostSetting.setMroSetting("mroWeightTE", 11.12);
		Assert.assertTrue(11.12 == mroNSNCostSetting.getMroSetting("mroWeightTE"));
		//checking with common setting
		mroNSNCostSetting.setMroSetting("pingpongCountThr", 12.12);
		Assert.assertTrue(12.12 == mroNSNCostSetting.getMroSetting("pingpongCountThr"));

	}

	@Test(expected=IllegalArgumentException.class)
	public void testMROSettingKDDICostinValid(){

		MROSettingItf mroNSNCostSetting = new  MROSettingKDDICost();

		mroNSNCostSetting.setMroSetting("mroWeightTEx", 11.12);
		Assert.assertTrue(11.12 == mroNSNCostSetting.getMroSetting("mroWeightTL"));

		//checking with common setting

		mroNSNCostSetting.setMroSetting("pingpongCountThr", 12.12);
		Assert.assertTrue(12.12 == mroNSNCostSetting.getMroSetting("pingpongCountThr"));

	}


	@Test(expected=IllegalArgumentException.class)
	public void testMROSettingKDDICostinValid2(){

		MROSettingItf mroNSNCostSetting = new  MROSettingKDDICost();
		//checking with common setting
		mroNSNCostSetting.setMroSetting("pingpongCountThr1", 12.12);

	}

	@Test(expected=IllegalArgumentException.class)
	public void testMROSettingKDDICostinValid3(){

		MROSettingItf mroNSNCostSetting = new  MROSettingKDDICost();

		//checking with common setting
		mroNSNCostSetting.setMroSetting("pingpongCountThr", 12.12);
		mroNSNCostSetting.getMroSetting("pingpongCountThr1");

	}

}
