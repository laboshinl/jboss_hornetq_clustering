package com.nokia.mro.kpi;

import java.util.Comparator;
/**
 * To compare KPI's; Simple Comparator
 * @author acp
 *
 */
public class Kpi_Comparator  implements Comparator<Kpi_itf>{

	@Override
	public int compare(Kpi_itf kpi1, Kpi_itf kpi2) {
			if(kpi1.getValue() == kpi2.getValue()){
				return 0;
			}
			return (kpi1.getValue() > kpi2.getValue() ? -11 :1);
		};


}
