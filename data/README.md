
This package includes data prepared for the  County of San Diego HHSA / 
California HealthCare Foundation Code-a-thon, on Fed 22, 2014. These datasets
are based on a scrape of [3-4-50 Data report for 2011]
(http://www.sdcounty.ca.gov/hhsa/programs/phs/documents/CHS-3-4-50DataReport_2013.pdf)
so it isn't official data. However, it is probably close, and the additional
demographic files may be useful. 

death-demographics: 3-4-50 Deaths, with Demographics
================================

The files in this directory are different formats for the same data, a 
combination of a scrape of San Diego County's 2001 3-4-50 Deaths report and 2012 
data for major races and ethinicities and veteran status. 

The formats included are: 

* .csv, For general data viewing
* .geojson, for use in D3
* .kml, for use with Google Maps
* ESRI Shapefile, for use with QGIS and other GIS programs. 

Fields in this dataset include:

* ogc_fid: Identifier in shapefiles for the SRA shape. 
* sra_id: SANDAG SRA Identifier
* name: Name of the SRA, using the name from teh 3-4-50 report
* year: Year of death. 
* all: Total number of deaths
* asthma: Count of deaths from Asthma
* cancer: Count of deaths from Cancer
* chd: Count of deaths  from Congestive Heart Disease
* copd: Count of deaths from Chronic Obstructive Pulmonary Disease
* diabetes: Count of deaths from diabetes
* stroke: Count of deaths from stroke
* deaths3450: Count of deaths from all 3-4-50 diseases
* pct3450: Percentage of all deaths from 3-4-50 diseases
* rate3450: Number of 3-4-50 deaths divided by the population
* est_pop: SRA population computed from the rate3450 and deaths3450 values
* total_pop: SRA Population estimated from the 2012 ACS
* area: area of the SRA in square km. 
* hispanic: Number of Hispanics in the SRA, from 2012 ACS. 
* white: Number of Whites in the SRA, from 2012 ACS. 
* black: Number of Black in the SRA, from 2012 ACS. 
* asian: Number of Asian in the SRA, from 2012 ACS. 
* veteran: Number of Veterans in the SRA, from 2012 ACS. 

The Race, Ethinicity and Veteran numbers are all based on the ACS, so if you 
want a population rate, divide by total_pop, not est_pop. 


demo-in-sra: Demographics Aggregated to SRAs
===============================

This file presents all of the demographics categories for the SRAs that are
included in the death-demographics datasets, but it also includes computations
the percentage of the people in that SRA who are of the given category, and the
population density for each category. 

You can link this file to death-demographics using the sra-id field. 

sra-crosswalk: Crosswalk between 3-4-50 and SANDAG names. 
=========================================================

The County's 3-4-50 files have names for each SRA, but don't include the 
SANDAG id number, and the names difer. This file provides a connection between 
the files. 


