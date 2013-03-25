Patent Metadata Analysis with Hadoop
===========================

* Mappers read "citingPatent,citationPatent" lines and output (citedPatent, citingPatent) tuples.
* Reducers concatenate citingPatents to a comma-separated string.

##Build

	mvn package

##Run

Run the jar file with Hadoop. Program arguments:

1. Path to the HDFS directory with *only* cite75_99.txt.
2. Path to the HDFS directory to save output files.

Example:

	hadoop jar target/HadoopPatents-1.0.jar edu.cooper.ece460.patents.HadoopPatents patents_in patents_out

	hadoop fs -getmerge patents_out outfile

Note that the equalized images directory must not already exist. It can be removed using:

	hadoop fs -rmr picasa_out 
