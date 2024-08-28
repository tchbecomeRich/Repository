# Quartz Getting Started

Quartz is a richly featured, open source job scheduling library that can be
integrated within virtually any Java application - from the smallest stand-alone
application to the largest e-commerce system.

### Reference Documentation

Core concepts
* Job: Specific content of scheduled execution
* JobDetail：Other configuration information related to the task
* Trigger：Describing the time rules for task execution
* The relationship between these components: Job:JobDetail -> 1:N; JobDetail:Trigger -> 1:N; Trigger:JobDetail -> 1:1

