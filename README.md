# QuantilCodingExercise
Server Cpu Usage Simulator


cd CpuUsage/src

./generate.sh DATA_PATH

Example : ./generate.sh C:\\Users\\Megha\\Documents\\Log.txt

Creates log for one day (24 hours past) from the time program is executed.

Ip addresses in the log file : (192.168.1.0 to 192.168.1.255, 192.168.2.0 to 192.168.2.255, 192.168.3.0 to 192.168.3.255, 192.168.4.0 to 192.168.4.231).

./query.sh DATA_PATH

Example : ./query.sh C:\\Users\\Megha\\Documents\\Log.txt

QUERY 192.168.1.10 0 2016-07-27 12:30 2016-07-27 12:35
Output
CPU usage on 192.168.1.10:
(2016-07-27 12:30,30%),(2016-07-27 12:31,70%),(2016-07-27 12:32,81%),(2016-07-27 12:33,75%),(2016-07-27 12:34,64%)

QUERY 192.168.2.11 1 2016-07-27 10:30 2016-07-27 10:35
Output
CPU usage on 192.168.2.11:
(2016-07-27 10:30,79%),(2016-07-27 10:31,63%),(2016-07-27 10:32,16%),(2016-07-27 10:33,46%),(2016-07-27 10:34,8%),

EXIT
