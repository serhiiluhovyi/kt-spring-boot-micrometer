# kt-spring-boot-micrometer

## aop-timed

```
GET http://localhost:8081/timed/hello
GET http://localhost:8081/actuator/prometheus

# HELP smth_time_seconds Time taken to return smth
# TYPE smth_time_seconds summary
smth_time_seconds{class="com.example.timedannotation.DemoService",exception="none",method="doSmth",type="aop-timed",quantile="0.5",} 0.654311424
smth_time_seconds{class="com.example.timedannotation.DemoService",exception="none",method="doSmth",type="aop-timed",quantile="0.95",} 0.889192448
smth_time_seconds_count{class="com.example.timedannotation.DemoService",exception="none",method="doSmth",type="aop-timed",} 10.0
smth_time_seconds_sum{class="com.example.timedannotation.DemoService",exception="none",method="doSmth",type="aop-timed",} 6.964222125
# HELP smth_time_seconds_max Time taken to return smth
# TYPE smth_time_seconds_max gauge
smth_time_seconds_max{class="com.example.timedannotation.DemoService",exception="none",method="doSmth",type="aop-timed",} 0.892753916

```



